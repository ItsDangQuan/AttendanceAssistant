package com.kttq.attendassist.features.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {
    val loginUIInfo by lazy {
        MutableStateFlow(
            LoginUIInfo("", "")
        )
    }

    fun login() {
        // Handle when user click `log in` button
    }

    fun onEmailChanged(string: String) {
        loginUIInfo.value = loginUIInfo.value.copy(email = string)
    }
    fun onPasswordChanged(string: String) {
        loginUIInfo.value = loginUIInfo.value.copy(password = string)
    }

    fun onPasswordVisibilityChanged() {
        loginUIInfo.value = loginUIInfo.value
            .copy(isPasswordVisible = !loginUIInfo.value.isPasswordVisible)
    }
}

data class LoginUIInfo(
    val email: String,
    val password: String,
    val isPasswordVisible: Boolean = false
)