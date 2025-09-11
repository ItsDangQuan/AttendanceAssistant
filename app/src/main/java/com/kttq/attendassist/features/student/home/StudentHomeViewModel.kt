package com.kttq.attendassist.features.student.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.ble.scanner.BleScanner
import com.kttq.attendassist.core.data.repositories.student.ClassRepository
import com.kttq.attendassist.core.data.repositories.student.RecordRepository
import com.kttq.attendassist.core.data.repositories.user.UserRepositoryRefactor
import com.kttq.attendassist.core.model.Record
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.util.DateManager
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val isLoading: Boolean = false,
    val isScanning: Boolean = false, // Added for scan status
    val error: String? = null,
)

data class StudentHomeStatSummary(
    val total: Int = 0,
    val attended: Int = 0,
    val leaveAccepted: Int = 0,
    val leaveUnaccepted: Int = 0
)

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    dateManager: DateManager,
    private val userRepository: UserRepositoryRefactor,
    private val recordRepository: RecordRepository,
    private val classRepository: ClassRepository,
    private val bleScanner: BleScanner
    // TODO: Inject a repository for fetching records
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentHomeUiState(isLoading = true))
    val uiState: StateFlow<StudentHomeUiState> = _uiState.asStateFlow()

    private val _userProfile = MutableStateFlow<StudentProfile?>(null)
    val userProfile: StateFlow<StudentProfile?> = _userProfile.asStateFlow()

    private val _allRecords = MutableStateFlow<List<Record>>(emptyList())
    val allRecords: StateFlow<List<Record>> = _allRecords.asStateFlow()

    val formattedDate = dateManager.formattedDate

    val statSummary: StateFlow<StudentHomeStatSummary> =
        allRecords.map { records ->
            // TODO: Potentially add other statuses like "absent" if they become relevant
            StudentHomeStatSummary(
                total = records.size,
                attended = records.count { it.status.equals("attended", ignoreCase = true) },
                leaveAccepted = records.count {
                    it.status.equals(
                        "leaveAccepted",
                        ignoreCase = true
                    )
                },
                leaveUnaccepted = records.count {
                    it.status.equals(
                        "leaveUnaccepted",
                        ignoreCase = true
                    )
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StudentHomeStatSummary() // Uses default values (all 0)
        )

    init {
        fetchStudentProfile()
        fetchAllStudentRecords()
    }

    private fun fetchStudentProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // TODO: Replace this in the future
            // val profile = userRepository.getCurrentUserAsStudent()
            // Now, we are fetching the code from the user table
            val profile = userRepository.getCurrentUserAsStudent()
            if (profile == null) {
                _uiState.update { it.copy(error = "User profile not found", isLoading = false) }
                return@launch
            }
            _userProfile.value = profile
            _uiState.update { it.copy(isLoading = false) }
        }
    }


    private fun fetchAllStudentRecords() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            if (_userProfile.value == null) {
                _uiState.update { it.copy(error = "User profile not found", isLoading = false) }
                return@launch
            }
            // TODO: Fetch using the other API for server validation
            val records = recordRepository.getRecordByStudentId(_userProfile.value!!.studentId)
            _allRecords.value = records
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    // onScanSuccess takes the session id as a string
    fun startScan(
        onScanSuccess: (String) -> Unit,
        onScanFailure: (Int) -> Unit
    ) {
        // TODO: Ensure Bluetooth permissions are granted before calling startScan

        bleScanner.startScan(
            onSuccess = { result ->
                //  Hmm, may be the result should combine classId and sessionId,
                //  The actual data may be not like this, i have just give an example
                val classId = result.substringBeforeLast("-")
                val sessionId = result.substringAfterLast("-")

                // I think that you will not agree with this,
                //  but this may be the best way to do it
                viewModelScope.launch {
                    if (classRepository.haveStudent(classId)) {
                        stopScan()
                        onScanSuccess(sessionId)
                    }
                }

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


    fun updateError(error: String?) {
        _uiState.update { it.copy(error = error) }
    }

}
