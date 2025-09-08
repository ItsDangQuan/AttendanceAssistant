package com.kttq.attendassist.features.student.lesson_info

import androidx.lifecycle.ViewModel
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
    val errorMessage: String? = null
)

@HiltViewModel
class StudentLessonInfoViewModel @Inject constructor(
    // TODO: Inject repositories for fetching lesson details and submitting absence request
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentLessonInfoUiState(isLoading = true))
    val uiState: StateFlow<StudentLessonInfoUiState> = _uiState.asStateFlow()

    init {
        loadLessonDetails()
    }

    private fun loadLessonDetails() {
        // TODO: Implement logic to load actual lesson details from a repository
        // For now, using placeholder data similar to what's currently in StudentLessonInfoScreen
        _uiState.update {
            it.copy(
                courseId = "Math",
                courseName = "MTH253",
                time = "9:00",
                date = "Aug 1st",
                presenter = "Mr.John Doe",
                isLoading = false
            )
        }
    }

    fun changeReasonToAbsent(newReason: String) {
        _uiState.update { it.copy(reasonToAbsent = newReason) }
    }

    fun changeWantToAbsent() {
        _uiState.update { currentState ->
            val wantsToAbs = !currentState.isWantToAbsent
            currentState.copy(
                isWantToAbsent = wantsToAbs,
                // Clear reason if student no longer wants to be absent
                reasonToAbsent = if (!wantsToAbs) null else currentState.reasonToAbsent
            )
        }
    }

    fun submitAbsenceRequest() {
        // TODO: Implement logic to submit the absence request
        // This will likely involve a call to a repository or use case.
        // Consider uiState.value.isWantToAbsent and uiState.value.reasonToAbsent
        println("Submit absence request: ${uiState.value}")
    }
}
