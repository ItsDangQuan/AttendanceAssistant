package com.kttq.attendassist.features.redirect

import androidx.lifecycle.ViewModel
import com.kttq.attendassist.core.data.repositories.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RedirectViewModel @Inject constructor(
    authRepository: AuthRepository
) : ViewModel() {
    val authState = authRepository.authState
}