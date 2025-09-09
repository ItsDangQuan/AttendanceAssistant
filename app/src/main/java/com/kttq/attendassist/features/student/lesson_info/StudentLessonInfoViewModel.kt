package com.kttq.attendassist.features.student.lesson_info

import android.bluetooth.le.ScanFilter
import androidx.lifecycle.ViewModel
import com.kttq.attendassist.core.ble.scanner.BleScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class StudentLessonInfoUiState(
    val courseId: String = "",
    val courseName: String = "",
    val time: String = "",
    val date: String = "",
    val presenter: String = "",
    val reasonToAbsent: String? = null,
    val isWantToAbsent: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val scannedLessonConfirmation: String? = null // To show confirmation or scanned data
)

@HiltViewModel
class StudentLessonInfoViewModel @Inject constructor(
    private val bleScanner: BleScanner
    // TODO: Inject repositories for fetching lesson details and submitting absence request
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentLessonInfoUiState(isLoading = true))
    val uiState: StateFlow<StudentLessonInfoUiState> = _uiState.asStateFlow()

    private val _isScanning = MutableStateFlow(false)
    val isScanning: StateFlow<Boolean> = _isScanning.asStateFlow()

    init {
        loadLessonDetails()
        _isScanning.value = bleScanner.isScanning() // Initialize based on current scanner state
    }

    private fun loadLessonDetails() {
        // TODO: Implement logic to load actual lesson details from a repository
        _uiState.update {
            it.copy(
                courseId = "MTH253", // Example course ID
                courseName = "Calculus III",
                time = "9:00 AM",
                date = "Oct 27th",
                presenter = "Prof. Newton",
                isLoading = false
            )
        }
    }

    fun startLessonScan() {
        if (bleScanner.isScanning()) {
            _isScanning.value = true // Ensure state is up-to-date
            println("Scan already in progress.")
            return
        }

        // TODO: Potentially create a ScanFilter based on uiState.value.courseId or other criteria
        val filters: List<ScanFilter>? = null // No filters for now, scans for any BLE device

        _uiState.update { it.copy(isLoading = true, errorMessage = null, scannedLessonConfirmation = null) }

        bleScanner.startScan(
            filter = filters,
            onSuccess = { receivedData ->
                // TODO: Validate receivedData against the current lesson (e.g., uiState.value.courseId)
                // For now, just display that data was received.
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        scannedLessonConfirmation = "Attendance recorded for: $receivedData. Please verify."
                    )
                }
                // isScanning might become false if scan stops after first result, or stay true if continuous
                _isScanning.value = bleScanner.isScanning()
                println("Scan success, received: $receivedData. Is still scanning: ${isScanning.value}")
                // Typically, you might want to automatically stop the scan here if successful for this use case
                 if(bleScanner.isScanning()) bleScanner.stopScan() // Auto-stop after successful detection for this example
                _isScanning.value = bleScanner.isScanning()
            },
            onFail = { errorCode ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Scan failed. Code: $errorCode"
                    )
                }
                _isScanning.value = false // Scan failed, so it's not scanning
                println("Scan failed with error code: $errorCode")
            }
        )
        // After calling startScan, update isScanning. It might have started and set the state immediately.
        _isScanning.value = bleScanner.isScanning()
    }

    fun stopLessonScan() {
        if (bleScanner.isScanning()) {
            bleScanner.stopScan()
        }
        _isScanning.value = bleScanner.isScanning() // Update based on actual scanner state after stopping
        _uiState.update { it.copy(isLoading = false) } // Ensure loading is false
        println("Scan stopped. Is scanning: ${isScanning.value}")
    }

    fun changeReasonToAbsent(newReason: String) {
        _uiState.update { it.copy(reasonToAbsent = newReason) }
    }

    fun changeWantToAbsent() {
        _uiState.update { currentState ->
            val wantsToAbs = !currentState.isWantToAbsent
            currentState.copy(
                isWantToAbsent = wantsToAbs,
                reasonToAbsent = if (!wantsToAbs) null else currentState.reasonToAbsent
            )
        }
    }

    fun submitAbsenceRequest() {
        // TODO: Implement logic to submit the absence request
        println("Submit absence request: ${uiState.value}")
        _uiState.update { it.copy(scannedLessonConfirmation = "Absence request submitted.") }
    }
}
