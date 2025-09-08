package com.kttq.attendassist.features.teacher.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherHomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TeacherHomeUiState(isLoading = true))
    val uiState: StateFlow<TeacherHomeUiState> = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
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
}

data class TeacherHomeUiState(
    val userName: String = "User", // Default name
    val isLoading: Boolean = false
)