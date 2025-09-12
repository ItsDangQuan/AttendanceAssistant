package com.kttq.attendassist.features.student.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import com.kttq.attendassist.core.data.repositories.student.StudentProfileRepository
import com.kttq.attendassist.core.model.StudentProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentProfileUiState(
    val isLoading: Boolean = false,
    val isLoggedOut: Boolean = false
    // TODO: Add any other profile-related state if needed
)

@HiltViewModel
class StudentProfileViewModel @Inject constructor(
    private val studentProfileRepository: StudentProfileRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentProfileUiState(isLoading = true))
    val uiState: StateFlow<StudentProfileUiState> = _uiState.asStateFlow()

    private val _user = MutableStateFlow<StudentProfile?>(null)
    val user: StateFlow<StudentProfile?> = _user.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val profile = studentProfileRepository.getCurrentStudentProfile()
                _user.value = profile
            } catch (_: Exception) {
                // Handle error
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onChangePasswordClicked() {
        // TODO: Implement navigation to change password screen or show a dialog.
        // This will likely require a new API endpoint and repository method.
        println("Change password clicked. Functionality not yet implemented.")
    }

    // TODO: Add function to update user profile if editable fields are introduced.
    // fun updateUserProfile(updatedState: StudentProfileUiState) { }

    fun onLogoutClicked() {
        viewModelScope.launch {
            authRepository.logout()
            _uiState.update { it.copy(isLoggedOut = true) }
        }
    }
}
