package com.kttq.attendassist.features.teacher.class_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiser
import com.kttq.attendassist.core.data.network.responses.Class // Existing import
import com.kttq.attendassist.core.data.network.responses.Session
import com.kttq.attendassist.core.data.network.responses.Student
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date // For session start/end times
import javax.inject.Inject


data class ClassDetailUiState(
    val isLoading: Boolean = true,
    val classDetails: Class? = null,
    val activeSessionId: String? = null,            // ID of the session currently being advertised
    val isAdvertising: Boolean = false,             // Reflects ViewModel's understanding of advertising state
    val error: String? = null
)

@HiltViewModel
class ClassDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bleAdvertiser: BleAdvertiser
    // TODO: Inject repositories (ClassRepository, SessionRepository, StudentRepository, UserRepository) when available
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
                // TODO: Fetch class details using ClassRepository
                // val details = classRepository.getClassDetails(classId)
                val details: Class? = null // Placeholder

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        classDetails = details,
                        error = if (details == null && classId.isNotBlank()) "Could not load class details for ID: $classId" else null
                    )
                }
            } catch (e: Exception) {
                // TODO: Log the exception e more specifically
                _uiState.update { it.copy(isLoading = false, error = "Failed to load class data. Please try again.") }
            }
        }
    }

    fun startNewAttendanceSession() {
        if (_uiState.value.isAdvertising || _uiState.value.activeSessionId != null) {
            _uiState.update { it.copy(error = "An attendance session is already active.") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            // TODO: Get currentTeacherId from UserRepository
            // val currentTeacherId = userRepository.getCurrentTeacherId()

            try {
                // TODO: Create session record in backend using SessionRepository
                // val newSession = sessionRepository.createNewSession(classId, currentTeacherId, Date().time)
                // Placeholder for new session creation:
                val newSessionId = "SESSION_" + System.currentTimeMillis()
                val newSession = Session(
                    newSessionId, "",
                    startTime = 0,
                    endTime = "",
                    teacherId = "",
                    className = "",
                    teacherName = "",
                    courseId = "",
                    courseName = "",
                )

                if (newSession != null) {
                    bleAdvertiser.startAdvertising(newSession.sessionId) 
                    // Update UI state to reflect that advertising has started
                    _uiState.update { it.copy(activeSessionId = newSession.sessionId, isLoading = false, isAdvertising = true) }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Failed to create new session record.") }
                }
            } catch (e: Exception) {
                // TODO: Log the exception e
                // If advertising failed to start, ensure isAdvertising is false
                _uiState.update { it.copy(isLoading = false, error = "Error starting new session: ${e.message}", isAdvertising = false) }
            }
        }
    }

    fun stopCurrentAttendanceSession() {
        val currentActiveSessionId = _uiState.value.activeSessionId
        // Check if we *think* we are advertising or have an active session
        if (currentActiveSessionId == null && !_uiState.value.isAdvertising) {
            _uiState.update { it.copy(error = "No active attendance session to stop.") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                bleAdvertiser.stopAdvertising()
                // Update UI state to reflect that advertising has stopped
                val newUiState = _uiState.value.copy(isAdvertising = false, isLoading = false)
                if (currentActiveSessionId != null) {
                    // TODO: Update session end time in backend using SessionRepository
                    // sessionRepository.endSession(currentActiveSessionId, Date().time)
                    _uiState.value = newUiState.copy(activeSessionId = null)
                } else {
                     _uiState.value = newUiState
                }
            } catch (e: Exception) {
                // TODO: Log the exception e
                // If stopping advertising failed, the actual state might be uncertain.
                // For now, we assume it stopped or will be stopped by the system eventually.
                _uiState.update { it.copy(isLoading = false, error = "Error stopping session: ${e.message}", isAdvertising = false) }
            }
        }
    }


    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
    
    // TODO: Add other necessary functions, e.g., navigating to a specific session's details or student details.
}
