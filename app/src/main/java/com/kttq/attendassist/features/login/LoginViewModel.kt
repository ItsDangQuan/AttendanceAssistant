package com.kttq.attendassist.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val loginUIInfo by lazy {
        MutableStateFlow(
            LoginUIInfo("", "")
        )
    }

    fun login() {
        viewModelScope.launch {
            loginUIInfo.value = loginUIInfo.value.copy(status = LoginStatus.LOADING)
            val result = authRepository.login(
                loginUIInfo.value.email,
                loginUIInfo.value.password
            )
            if (result.isSuccess) {
                onStatusChanged(LoginStatus.SUCCESS)
            } else {
                onStatusChanged(LoginStatus.ERROR)
            }
        }
    }

    fun onEmailChanged(string: String) {
        loginUIInfo.value = loginUIInfo.value.copy(email = string)
    }
    fun onPasswordChanged(string: String) {
        loginUIInfo.value = loginUIInfo.value.copy(password = string)
    }

    fun onStatusChanged(status: LoginStatus) {
        loginUIInfo.value = loginUIInfo.value.copy(status = status)
    }

    fun onPasswordVisibilityChanged() {
        loginUIInfo.value = loginUIInfo.value
            .copy(isPasswordVisible = !loginUIInfo.value.isPasswordVisible)
    }
}

data class LoginUIInfo(
    val email: String,
    val password: String,
    val isPasswordVisible: Boolean = false,
    val status: LoginStatus = LoginStatus.NONE
)

enum class LoginStatus {
    NONE,
    LOADING,
    SUCCESS,
    ERROR
}