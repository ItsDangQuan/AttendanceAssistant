package com.kttq.attendassist.features.student.session_confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.data.repositories.student.StudentSessionRepository
import com.kttq.attendassist.core.model.StudentSession
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
    val studentSession: StudentSession? = null,
    val error: String? = null,
    val attendanceConfirmed: Boolean = false
)

@HiltViewModel
class SessionConfirmViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val studentSessionRepository: StudentSessionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SessionConfirmUiState())
    val uiState: StateFlow<SessionConfirmUiState> = _uiState.asStateFlow()

    private val sessionId = savedStateHandle.toRoute<Destination.Student.SessionConfirm>().sessionId

    init {
        fetchCurrentSessionDetails(sessionId)
//        } else {
//            _uiState.update { it.copy(error = "Invalid Session ID provided.", isLoading = false) }
//        }
    }

    private fun fetchCurrentSessionDetails(sessionId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // In here, we should fetch the session details from the repository, using the sessionId
                val sessionDetails = studentSessionRepository.getStudentSessionById(sessionId)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        studentSession = sessionDetails,
                        error = if (sessionDetails == null) "Session not found (ID: $sessionId)" else null
                    )
                }
            } catch (_: Exception) {
                // TODO: Log the exception e
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load session details. Please try again."
                    )
                }
            }
        }
    }


    fun confirmAttendance(): Boolean {
        val currentSession = _uiState.value.studentSession
        if (currentSession == null) {
            _uiState.update { it.copy(error = "Session details not available. Cannot confirm attendance.") }
            return false // Confirmation cannot be initiated
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val success = studentSessionRepository.confirmAttendance(sessionId)

                if (success) {
                    _uiState.update { it.copy(isLoading = false, attendanceConfirmed = true) }
                    // TODO: Implement post-confirmation action, e.g., navigate to a success screen or back to home with a message.
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Failed to confirm attendance. The session may no longer be active or an error occurred."
                        )
                    }
                }
            } catch (_: Exception) {
                // TODO: Log the exception e
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "An error occurred while confirming attendance. Please try again."
                    )
                }
            }
        }
        return _uiState.value.attendanceConfirmed
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

}
