package com.kttq.attendassist.features.teacher.lesson_info

import androidx.lifecycle.ViewModel
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
    // TODO: Inject repositories here when available
) : ViewModel() {

    private val _lessonDetailsUiState = MutableStateFlow<LessonDetailsUiState?>(null)
    val lessonDetailsUiState: StateFlow<LessonDetailsUiState?> = _lessonDetailsUiState.asStateFlow()

    private val _attendanceStatsUiState = MutableStateFlow<AttendanceStatsUiState?>(null)
    val attendanceStatsUiState: StateFlow<AttendanceStatsUiState?> = _attendanceStatsUiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        // TODO: Implement logic to load lesson details from a repository
        // This would populate _lessonDetailsUiState.value
        // For example:
        // viewModelScope.launch {
        //     _lessonDetailsUiState.value = repository.getLessonDetails(lessonId)
        // }

        // TODO: Implement logic to load attendance statistics from a repository
        // This would populate _attendanceStatsUiState.value
        // For example:
        // viewModelScope.launch {
        //     _attendanceStatsUiState.value = repository.getAttendanceStats(lessonId)
        // }
    }

    fun onStopCheckingClicked() {
        // TODO: Implement logic to stop the attendance checking process
        // This will likely involve a call to a repository or use case
        println("Stop checking clicked")
    }

    fun onExportClicked() {
        // TODO: Implement logic to export attendance data
        // This could involve generating a file (e.g., CSV, PDF) and sharing it
        println("Export clicked")
    }
}
