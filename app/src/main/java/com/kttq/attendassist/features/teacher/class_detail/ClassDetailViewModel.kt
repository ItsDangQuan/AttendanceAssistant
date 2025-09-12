package com.kttq.attendassist.features.teacher.class_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiser
import com.kttq.attendassist.core.data.repositories.teacher.TeacherClassRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherProfileRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherSessionRepository
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.util.ClassSessionCodec
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject


data class ClassDetailUiState(
    val isLoading: Boolean = true,
    val classDetails: Class? = null,
    val activeSessionId: String? = null,            // ID of the session currently being advertised
    // val isAdvertising: Boolean = false,             // Reflects ViewModel's understanding of advertising state
    val error: String? = null
)

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bleAdvertiser: BleAdvertiser,
    private val teacherClassRepository: TeacherClassRepository,
    private val teacherSessionRepository: TeacherSessionRepository,
    private val teacherUserRepository: TeacherProfileRepository,
) : ViewModel() {

    val classId: String = savedStateHandle.toRoute<Destination.Teacher.ClassDetail>().classId

    private val _uiState = MutableStateFlow(ClassDetailUiState())
    val uiState: StateFlow<ClassDetailUiState> = _uiState.asStateFlow()

    // Removed: private val _isAdvertising: StateFlow<Boolean> = bleAdvertiser.isAdvertising
    // Removed: observeAdvertisingState() method, as BleAdvertiser.isAdvertising is a Boolean, not a Flow.
    // For a reactive approach, BleAdvertiser should expose its state as a Flow.

    init {

        if (classId.isNotBlank()) {
            fetchData()
            // TODO: If BleAdvertiser offers a way to get initial advertising state (e.g., a simple boolean getter),
            // you might want to initialize _uiState.isAdvertising with it here, though it won't be reactive.
            // For now, it defaults to false in ClassDetailUiState.
        } else {
            _uiState.update { it.copy(isLoading = false, error = "Class ID not found.") }
        }
    }

    fun fetchData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val details = teacherClassRepository.getClassById(classId)
                Log.d("ClassDetailViewModel", "fetchData: $details")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        classDetails = details,
                        error = if (details == null && classId.isNotBlank()) "Could not load class details for ID: $classId" else null
                    )
                }
            } catch (e: Exception) {
                // TODO: Log the exception e more specifically
                Log.d("ClassDetailViewModel", "fetchData: $e")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to load class data. Please try again."
                    )
                }
            }
        }
    }

    fun startNewAttendanceSession() {
        if (/* _uiState.value.isAdvertising  || */ _uiState.value.activeSessionId != null) {
            _uiState.update { it.copy(error = "An attendance session is already active.") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }


            try {
                val teacherId = teacherUserRepository.getCurrentTeacherProfile()?.teacherId
                val newSession =
                    teacherSessionRepository.createNewSession(classId, teacherId ?: "N/A")
                val sessionUuid = UUID.fromString(newSession.sessionId)


                bleAdvertiser.startAdvertising(
                    ClassSessionCodec.pack(newSession.classId, sessionUuid)
                )
                _uiState.update {
                    it.copy(
                        activeSessionId = newSession.sessionId,
                        isLoading = false,
                        // isAdvertising = true
                    )
                }

            } catch (e: Exception) {
                // TODO: Log the exception e
                // If advertising failed to start, ensure isAdvertising is false
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error starting new session: ${e.message}",
                        // isAdvertising = false
                    )
                }
            }
        }
    }

//     fun stopCurrentAttendanceSession() {
//         val currentActiveSessionId = _uiState.value.activeSessionId
//         // Check if we *think* we are advertising or have an active session
//         if (currentActiveSessionId == null && !_uiState.value.isAdvertising) {
//             _uiState.update { it.copy(error = "No active attendance session to stop.") }
//             return
//         }
//
//         viewModelScope.launch {
//             _uiState.update { it.copy(isLoading = true, error = null) }
//             try {
//                 bleAdvertiser.stopAdvertising()
//                 // Update UI state to reflect that advertising has stopped
//                 val newUiState = _uiState.value.copy(isAdvertising = false, isLoading = false)
//                 if (currentActiveSessionId != null) {
//                     // TODO: Update session end time in backend using SessionRepository
//                     // sessionRepository.endSession(currentActiveSessionId, Date().time)
//                     _uiState.value = newUiState.copy(activeSessionId = null)
//                 } else {
//                      _uiState.value = newUiState
//                 }
//             } catch (e: Exception) {
//                 // TODO: Log the exception e
//                 // If stopping advertising failed, the actual state might be uncertain.
//                 // For now, we assume it stopped or will be stopped by the system eventually.
//                 _uiState.update { it.copy(isLoading = false, error = "Error stopping session: ${e.message}", isAdvertising = false) }
//             }
//         }
//     }


    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    fun resetUiState(){
        _uiState.update {
            it.copy(
                isLoading = true,
                activeSessionId = null,
                // isAdvertising = false,
                error = null
            )
        }
    }
    // TODO: Add other necessary functions, e.g., navigating to a specific session's details or student details.
}
