package com.kttq.attendassist.features.student.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.ble.scanner.BleScanner
import com.kttq.attendassist.core.data.network.models.Record
import com.kttq.attendassist.core.data.network.models.Session
import com.kttq.attendassist.core.data.repositories.user.UserRepository
import com.kttq.attendassist.core.util.DateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentHomeUiState(
    val userName: String = "User", // Default name
    val isLoading: Boolean = false,
    val isScanning: Boolean = false, // Added for scan status
    val error: String? = null,
    // TODO: Add state for incoming classes list
    // TODO: Add state for today's classes list
)

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    dateManager: DateManager,
    private val userRepository: UserRepository,
    private val bleScanner: BleScanner
    // TODO: Inject a repository for fetching records
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentHomeUiState(isLoading = true))
    val uiState: StateFlow<StudentHomeUiState> = _uiState.asStateFlow()

    private val _allRecords = MutableStateFlow<List<Record>>(emptyList())
    val allRecords: StateFlow<List<Record>> = _allRecords.asStateFlow()

    val formattedDate = dateManager.formattedDate

    val statSummary: StateFlow<List<Pair<String, String>>> =
        allRecords.map { records ->
            val totalCount = records.size
            val attendedCount = records.count { it.status.equals("attended", ignoreCase = true) }
            val leaveAcceptedCount =
                records.count { it.status.equals("leaveAccepted", ignoreCase = true) }
            val leaveUnacceptedCount =
                records.count { it.status.equals("leaveUnaccepted", ignoreCase = true) }
            // TODO: Potentially add other statuses like "absent" if they become relevant

            listOf(
                "Total" to totalCount.toString(),
                "Attended" to attendedCount.toString(),
                "Leave Accepted" to leaveAcceptedCount.toString(),
                "Leave Unaccepted" to leaveUnacceptedCount.toString()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf(
                "Total" to "0",
                "Attended" to "0",
                "Leave Accepted" to "0",
                "Leave Unaccepted" to "0"
            ) // Initial default values
        )

    init {
        fetchInitialData()
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = userRepository.getCurrentUser()
            _uiState.update {
                it.copy(
                    userName = user?.firstName ?: user?.email ?: "User",
                )
            }
            fetchAllStudentRecords()
        }
    }

    private suspend fun getAllRecordsFromRepository(): List<Record> {
        // TODO: Implement actual logic to fetch all records for the student from a repository/API
        // This might involve using the current user's ID.
        delay(1000) // Simulate network delay
        return emptyList() // Placeholder
    }

    private fun fetchAllStudentRecords() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val records = getAllRecordsFromRepository()
            _allRecords.value = records
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun startScan(
        onScanSuccess: (Session) -> Unit,
        onScanFailure: (Int) -> Unit
    ) {
        // TODO: Ensure Bluetooth permissions are granted before calling startScan
        // TODO: Handle scan results, e.g., by collecting a Flow from bleScanner or via a callback
        bleScanner.startScan(
            onSuccess = { result ->
                stopScan()
                // TODO: parse the string into the session id
                //  Sent that to the server to get the new session
                //  Currently just returning a dummy session
                onScanSuccess(
                    Session(
                        "",
                        "",
                        0,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                    )
                )
            },
            onFail = { errorCode ->
                stopScan()
                onScanFailure(errorCode)
            }
        )
        _uiState.update { it.copy(isScanning = true) }
    }

    fun stopScan() {
        bleScanner.stopScan()
        _uiState.update { it.copy(isScanning = false) }
    }

    // fun onIncomingClassSeeAllClicked() {
    //     // TODO: Implement navigation to the full list of incoming classes
    //     // TODO: Or fetch more incoming classes data if displaying a preview
    //     println("Navigate to all incoming classes screen or fetch data.")
    // }

    // fun onClassTodaySeeAllClicked() {
    //     // TODO: Implement navigation to the full list of today's classes
    //     // TODO: Or fetch more today's classes data if displaying a preview
    //     println("Navigate to all today's classes screen or fetch data.")
    // }

    // // TODO: Add function to fetch incoming classes
    // // fun fetchIncomingClasses() { }

    // // TODO: Add function to fetch today's classes
    // // fun fetchTodayClasses() { }

    fun updateError(error: String?) {
        _uiState.update { it.copy(error = error) }
    }
}
