package com.kttq.attendassist.features.teacher.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.teacher.SessionRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherProfileRepository
import com.kttq.attendassist.core.model.TeacherProfile
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.util.DateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherHomeViewModel @Inject constructor(
    dateManager: DateManager,
    private val teacherProfileRepository: TeacherProfileRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TeacherHomeUiState(isLoading = true))
    val uiState: StateFlow<TeacherHomeUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<TeacherProfile?>(null)
    val user: StateFlow<TeacherProfile?> = _user.asStateFlow()

    val formattedDate = dateManager.formattedDate

    private val _recentSession = MutableStateFlow<List<TeacherSession>>(emptyList())
    val recentSession: StateFlow<List<TeacherSession>> = _recentSession.asStateFlow()

    init {
        fetchCurrentUser()
        fetchRecentSession()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _user.value = teacherProfileRepository.getCurrentTeacherProfile()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun fetchRecentSession() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _recentSession.value = sessionRepository.getRecentSession()
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class TeacherHomeUiState(
    val isLoading: Boolean = false
)