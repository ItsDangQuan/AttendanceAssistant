package com.kttq.attendassist.features.teacher.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import com.kttq.attendassist.core.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TeacherProfileUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val studentId: String = "",
    val email: String = "",
    val phone: String = "",
    val isLoggedout: Boolean = false
    // TODO: Add any other profile-related state if needed
)

@HiltViewModel
class TeacherProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TeacherProfileUiState(isLoading = true))
    val uiState: StateFlow<TeacherProfileUiState> = _uiState.asStateFlow()

    init {
        fetchUserProfile()
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = userRepository.getCurrentUser()
            if (user != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        name = "${user.firstName ?: ""} ${user.lastName ?: ""}".trim(),
                        // Assuming userId can serve as studentId for now.
                        // TODO: Confirm if there's a separate studentId field or if userId is appropriate.
                        studentId = user.userId.toString(),
                        email = user.email,
                        // TODO: UserOut does not currently contain a phone number.
                        //  Update UserOut and service if phone is available from the API.
                        phone = "" // Placeholder for phone
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        // Set default/error values or handle appropriately
                        name = "N/A",
                        studentId = "N/A",
                        email = "N/A",
                        phone = "N/A"
                    )
                }
            }
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
