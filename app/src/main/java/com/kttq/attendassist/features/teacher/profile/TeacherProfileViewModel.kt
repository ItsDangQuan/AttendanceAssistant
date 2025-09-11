package com.kttq.attendassist.features.teacher.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import com.kttq.attendassist.core.data.repositories.user.UserRepository
import com.kttq.attendassist.core.data.repositories.user.UserRepositoryRefactor
import com.kttq.attendassist.core.model.TeacherProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TeacherProfileUiState(
    val isLoading: Boolean = false,
    val isLoggedout: Boolean = false
    // TODO: Add any other profile-related state if needed
)

@HiltViewModel
class TeacherProfileViewModel @Inject constructor(
    private val userRepository: UserRepositoryRefactor,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherProfileUiState(isLoading = true))
    val uiState: StateFlow<TeacherProfileUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<TeacherProfile?>(null)
    val user: StateFlow<TeacherProfile?> = _user.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _user.value = userRepository.getCurrentUserAsTeacher()
            _uiState.update { it.copy( isLoading = false, ) }
        }
    }

    fun onChangePasswordClicked() {
        // TODO: Implement navigation to change password screen or show a dialog.
        // This will likely require a new API endpoint and repository method.
        println("Change password clicked. Functionality not yet implemented.")
    }

    // TODO: Add function to update user profile if editable fields are introduced.
    // fun updateUserProfile(updatedState: TeacherProfileUiState) { }

    fun onLogoutClicked() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isLoggedout = true) }
        }
    }
}
