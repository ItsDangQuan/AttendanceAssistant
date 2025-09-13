package com.kttq.attendassist.features.teacher.class_current_session

import android.util.Log
import com.kttq.attendassist.core.model.StudentProfile


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiser
import com.kttq.attendassist.core.data.repositories.teacher.TeacherClassRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherSessionRepository
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ClassCurrentSessionUiState(
    val isStop: Boolean = false
)

@HiltViewModel
class ClassCurrentSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teacherClassRepository: TeacherClassRepository,
    private val teacherSessionRepository: TeacherSessionRepository,
    private val bleAdvertiser: BleAdvertiser
) : ViewModel() {

    val sessionId = savedStateHandle.toRoute<Destination.Teacher.ClassCurrentSession>().sessionId

    private val _uiState = MutableStateFlow(ClassCurrentSessionUiState())
    val uiState: StateFlow<ClassCurrentSessionUiState> = _uiState.asStateFlow()

    private val _studentsList = MutableStateFlow<List<StudentProfile>>(emptyList())
    val studentsList: StateFlow<List<StudentProfile>> = _studentsList.asStateFlow()

    private val _currentsStudentsList = MutableStateFlow<List<StudentProfile>>(emptyList())
    val currentsStudentsList: StateFlow<List<StudentProfile>> = _currentsStudentsList.asStateFlow()

    private var pollingJob: Job? = null

    init {
        startPolling()
    }

    private fun startPolling() {
        pollingJob?.cancel() // prevent duplicate jobs
        pollingJob = viewModelScope.launch {
            val currentSession = teacherSessionRepository.getSessionBySessionId(sessionId)
            if (currentSession == null) return@launch

            while (isActive) {
                try {
                    _studentsList.value =
                        teacherClassRepository.getAllStudentProfileInClass(currentSession.classId)?:emptyList()

                    _currentsStudentsList.value =
                        teacherSessionRepository.getStudentInSession(
                            currentSession.classId,
                            currentSession.sessionId
                        ) ?:emptyList()
                } catch (e: Exception) {
                    // log error or update UI state
                }

                delay(5_000) // refresh every 5 seconds
            }
        }
    }

    fun stopCurrentAttendanceSession() {
        viewModelScope.launch {
            bleAdvertiser.stopAdvertising()
            pollingJob?.cancel() // ⬅️ stops refresh loop immediately
            _uiState.value = _uiState.value.copy(isStop = true)
        }
    }
}

