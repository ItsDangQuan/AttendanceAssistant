package com.kttq.attendassist.features.teacher.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.network.responses.Session
import com.kttq.attendassist.core.data.repositories.user.UserRepository
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
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TeacherHomeUiState(isLoading = true))
    val uiState: StateFlow<TeacherHomeUiState> = _uiState.asStateFlow()

    val formattedDate = dateManager.formattedDate

    private val _recentSession = MutableStateFlow<List<Session>>(emptyList())
    val recentSession: StateFlow<List<Session>> = _recentSession.asStateFlow()

    init {
        fetchCurrentUser()
        fetchRecentSession()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = userRepository.getCurrentUser()
            _uiState.update {
                it.copy(
                    userName = user?.firstName ?: user?.email ?: "User",
                    isLoading = false
                )
            }
        }
    }

    private fun fetchRecentSession() {
        viewModelScope.launch {
            // TODO: Fetch recent session from repository
            // _recentSession.value = userRepository.getRecentSession()
            // Now, we are using empty list as mock data
            _recentSession.value = emptyList()
        }
    }
    // fun logout() {
    //     viewModelScope.launch {
    //         userRepository.logout()
    //     }
    // }
}

data class TeacherHomeUiState(
    val userName: String = "User", // Default name
    val isLoading: Boolean = false
)