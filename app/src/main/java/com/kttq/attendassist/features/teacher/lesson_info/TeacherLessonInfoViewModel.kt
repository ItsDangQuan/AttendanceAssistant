package com.kttq.attendassist.features.teacher.lesson_info

import androidx.lifecycle.ViewModel
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class LessonDetailsUiState(
    val courseId: String = "",
    val courseName: String = "",
    val time: String = "",
    val date: String = "",
    val presenter: String = ""
)

data class AttendanceStatsUiState(
    val totalStudents: Int = 0,
    val checkedStudents: Int = 0
)

@HiltViewModel
class TeacherLessonInfoViewModel @Inject constructor(
    private val bleAdvertiser: BleAdvertiser
    // TODO: Inject other repositories here when available
) : ViewModel() {

    private val _lessonDetailsUiState = MutableStateFlow<LessonDetailsUiState?>(null)
    val lessonDetailsUiState: StateFlow<LessonDetailsUiState?> = _lessonDetailsUiState.asStateFlow()

    private val _attendanceStatsUiState = MutableStateFlow<AttendanceStatsUiState?>(null)
    val attendanceStatsUiState: StateFlow<AttendanceStatsUiState?> = _attendanceStatsUiState.asStateFlow()

    private val _isChecking = MutableStateFlow(false) // Initialized to false, then updated in init
    val isChecking: StateFlow<Boolean> = _isChecking.asStateFlow()

    init {
        loadInitialData()
        // Initialize isChecking based on the advertiser's current state at ViewModel creation
        _isChecking.value = bleAdvertiser.isAdvertising()
    }

    private fun loadInitialData() {
        // TODO: Implement logic to load lesson details from a repository
        // This would populate _lessonDetailsUiState.value. For example:
        // viewModelScope.launch {
        //     _lessonDetailsUiState.value = repository.getLessonDetails(lessonId)
        // }

        // TODO: Implement logic to load attendance statistics from a repository
        // This would populate _attendanceStatsUiState.value. For example:
        // viewModelScope.launch {
        //     _attendanceStatsUiState.value = repository.getAttendanceStats(lessonId)
        // }
    }

    fun startCheckingAttendance() {
        // TODO: Construct meaningful data from lessonDetailsUiState or other sources
        val dataToAdvertise = lessonDetailsUiState.value?.courseId ?: "defaultLessonData"

        if (bleAdvertiser.isAdvertising()) {
            // If already advertising, ensure our state reflects this.
            // This could happen if startCheckingAttendance is called multiple times
            // or if the state was externally set and init didn't catch it recently.
            if (!_isChecking.value) {
                 _isChecking.value = true
            }
            println("Already checking attendance.")
            return
        }

        bleAdvertiser.startAdvertising(
            data = dataToAdvertise,
            onSuccess = {
                _isChecking.value = true // Respond to successful start
                println("BLE Advertising started successfully.")
            },
            onFail = { errorCode ->
                _isChecking.value = false // Respond to failed start
                // TODO: Handle advertising start failure (e.g., show a message to the user)
                println("BLE Advertising failed to start with error code: $errorCode")
            }
        )
    }

    fun onStopCheckingClicked() {
        if (bleAdvertiser.isAdvertising()) {
            bleAdvertiser.stopAdvertising()
            // After attempting to stop, update _isChecking based on the current actual state
            // This ensures it reflects the change resulting from stopAdvertising()
            _isChecking.value = bleAdvertiser.isAdvertising()
            if (_isChecking.value) {
                println("BLE Advertising failed to stop or was restarted externally.")
            } else {
                println("BLE Advertising stopped successfully.")
            }
        } else {
            // If not advertising, ensure our state reflects that.
            _isChecking.value = false
            println("BLE Advertising was not active.")
        }
    }

    fun onExportClicked() {
        // TODO: Implement logic to export attendance data
        // This could involve generating a file (e.g., CSV, PDF) and sharing it
        println("Export clicked")
    }
}
