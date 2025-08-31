package com.kttq.attendassist.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import com.kttq.attendassist.core.domain.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val loginUiInfo by lazy {
        MutableStateFlow(
            LoginUiInfo("", "")
        )
    }

    fun login() {
        loginUiInfo.value = loginUiInfo.value.copy(status = LoginStatus.LOADING)
        viewModelScope.launch {
            val result = authRepository.login(
                UserLogin(
                    loginUiInfo.value.email,
                    loginUiInfo.value.password
                )
            ).collect { result ->
                when (result) {
                    AuthState.UNAUTHENTICATED ->
                        onStatusChanged(LoginStatus.ERROR)

                    AuthState.LOADING_ROLE ->
                        onStatusChanged(LoginStatus.LOADING)

                    AuthState.AUTHENTICATED_STUDENT,
                    AuthState.AUTHENTICATED_TEACHER -> {
                        onStatusChanged(LoginStatus.SUCCESS)
                    }
                }
            }
        }
    }

    fun onEmailChanged(string: String) {
        loginUiInfo.value = loginUiInfo.value.copy(email = string)
    }

    fun onPasswordChanged(string: String) {
        loginUiInfo.value = loginUiInfo.value.copy(password = string)
    }

    fun onStatusChanged(status: LoginStatus) {
        loginUiInfo.value = loginUiInfo.value.copy(status = status)
    }

    fun onPasswordVisibilityChanged() {
        loginUiInfo.value = loginUiInfo.value
            .copy(isPasswordVisible = !loginUiInfo.value.isPasswordVisible)
    }
}

data class LoginUiInfo(
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