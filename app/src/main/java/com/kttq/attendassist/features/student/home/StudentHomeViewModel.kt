package com.kttq.attendassist.features.student.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.ble.scanner.BleScanner
import com.kttq.attendassist.core.data.repositories.student.StudentClassRepository
import com.kttq.attendassist.core.data.repositories.student.StudentProfileRepository
import com.kttq.attendassist.core.data.repositories.student.StudentRecordRepository
import com.kttq.attendassist.core.model.Record
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.util.ClassSessionCodec
import com.kttq.attendassist.core.util.DateTimeManager
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
    val absent: Int = 0,
)

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    dateTimeManager: DateTimeManager,
    private val studentProfileRepository: StudentProfileRepository,
    private val studentRecordRepository: StudentRecordRepository,
    private val studentClassRepository: StudentClassRepository,
    private val bleScanner: BleScanner
    // TODO: Inject a repository for fetching records
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentHomeUiState(isLoading = true))
    val uiState: StateFlow<StudentHomeUiState> = _uiState.asStateFlow()

    private val _userProfile = MutableStateFlow<StudentProfile?>(null)
    val userProfile: StateFlow<StudentProfile?> = _userProfile.asStateFlow()

    private val _allRecords = MutableStateFlow<List<Record>>(emptyList())
    val allRecords: StateFlow<List<Record>> = _allRecords.asStateFlow()

    private val _scanResult = MutableStateFlow<List<Pair<Int, Int>>>(emptyList())
    val scanResult: StateFlow<List<Pair<Int, Int>>> = _scanResult.asStateFlow()
    val formattedDate = dateTimeManager.formattedDate

    val statSummary: StateFlow<StudentHomeStatSummary> =
        allRecords.map { records ->
            StudentHomeStatSummary(
                total = records.size,
                attended = records.count { it.status.equals("presented", ignoreCase = true) },
                absent = records.count { it.status.equals("absent", ignoreCase = true) }
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = StudentHomeStatSummary() // Uses default values (all 0)
        )

    init {
        viewModelScope.launch {
            fetchStudentProfile()
            fetchAllStudentRecords()
        }
    }

    private suspend fun fetchStudentProfile() {
        _uiState.update { it.copy(isLoading = true) }
        val profile = studentProfileRepository.getCurrentStudentProfile()
        if (profile == null) {
            _uiState.update { it.copy(error = "User profile not found", isLoading = false) }
            return
        }
        _userProfile.value = profile
        _uiState.update { it.copy(isLoading = false) }
    }


    private suspend fun fetchAllStudentRecords() {
        _uiState.update { it.copy(isLoading = true) }
        if (_userProfile.value == null) {
            Log.d("StudentHomeViewModel", "User profile is null")
            return
        }
        val records = studentRecordRepository.getRecordByStudentId(_userProfile.value!!.studentId)!!
        _allRecords.value = records
        _uiState.update { it.copy(isLoading = false) }
    }

    // onScanSuccess takes the session id as a string
    fun startScan(
        onScanSuccess: (Int) -> Unit,
        onScanFailure: (Int) -> Unit
    ) {
        // TODO: Ensure Bluetooth permissions are granted before calling startScan

        clearScanResult()
        Log.d("StudentHomeViewModel", "Scan started")
        _uiState.update { it.copy(isScanning = true) }
        bleScanner.startScan(
            onSuccess = { result ->
                Log.d("StudentHomeViewModel", "Scan result: $result")
                //  Hmm, may be the result should combine classId and sessionId,
                //  The actual data may be not like this, i have just give an example
                val res = ClassSessionCodec.unpack(result)
                // I think that you will not agree with this,
                //  but this may be the best way to do it
                val classIdRes = res.first
                val sessionIdRes = res.second
                viewModelScope.launch {
                    if (studentClassRepository.haveStudent(classIdRes) == true) {
                        _scanResult.update {
                            it + Pair(classIdRes, sessionIdRes)
                        }
                    }
                }
                if (_scanResult.value.isNotEmpty()) {
                    bleScanner.stopScan()
                    true
                } else false

            },
            onFail = { errorCode ->
                bleScanner.stopScan()
                onScanFailure(errorCode)
            }
        )
    }

    fun stopScan() {
        bleScanner.stopScan()
        Log.d("StudentHomeViewModel", "Scan stopped")
        _uiState.update { it.copy(isScanning = false) }
    }

    fun clearScanResult() {
        _scanResult.value = emptyList()
    }

    fun updateError(error: String?) {
        _uiState.update { it.copy(error = error) }
    }

}

