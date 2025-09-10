package com.kttq.attendassist.features.student.session_confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.model.Session
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SessionConfirmUiState(
    val isLoading: Boolean = false,
    val session: Session? = null,
    val error: String? = null,
    val attendanceConfirmed: Boolean = false
)

@HiltViewModel
class SessionConfirmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionConfirmUiState())
    val uiState: StateFlow<SessionConfirmUiState> = _uiState.asStateFlow()

    private val sessionId = savedStateHandle.toRoute<Destination.Student.SessionConfirm>().sessionId

    init {
        if (sessionId.isNotBlank()) {
            fetchCurrentSessionDetails(sessionId)
        } else {
            _uiState.update { it.copy(error = "Invalid Session ID provided.", isLoading = false) }
        }
    }

    private fun fetchCurrentSessionDetails(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // TODO: Replace with actual SessionRepository implementation if it returns a Result or handles errors differently
                val sessionDetails = Session(
                    sessionId = "",
                    classId = "",
                    startTime = 0,
                    endTime = "",
                    teacherId = "",
                    className = "",
                    teacherName = "",
                    courseId = "",
                    courseName = ""
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        session = sessionDetails,
                        error = if (sessionDetails == null) "Session not found (ID: $id)" else null
                    )
                }
            } catch (e: Exception) {
                // TODO: Log the exception e
                _uiState.update { it.copy(isLoading = false, error = "Failed to load session details. Please try again.") }
            }
        }
    }

    fun confirmAttendance() {
        val currentSession = _uiState.value.session
        if (currentSession == null) {
            _uiState.update { it.copy(error = "Session details not available. Cannot confirm attendance.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // TODO: Replace by real repo
                val studentId = ""
                if (studentId == null) {
                    _uiState.update { it.copy(isLoading = false, error = "Unable to identify student. Please log in again.") }
                    return@launch
                }

                // TODO: Replace with actual AttendanceRepository implementation
                val success = true
                if (success) {
                    _uiState.update { it.copy(isLoading = false, attendanceConfirmed = true) }
                    // TODO: Implement post-confirmation action, e.g., navigate to a success screen or back to home with a message.
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Failed to confirm attendance. The session may no longer be active or an error occurred.") }
                }
            } catch (e: Exception) {
                // TODO: Log the exception e
                _uiState.update { it.copy(isLoading = false, error = "An error occurred while confirming attendance. Please try again.") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

}
