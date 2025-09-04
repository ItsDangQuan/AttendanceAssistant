package com.kttq.attendassist.features.redirect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.core.domain.model.AuthState

@Composable
fun RedirectRoute(
    navigateToLogin: () -> Unit,
    navigateToStudent: () -> Unit,
    navigateToTeacher: () -> Unit,
    modifier: Modifier = Modifier,
    redirectViewModel: RedirectViewModel = hiltViewModel()
) {
    val auth by redirectViewModel.authState.collectAsState(null)

    LaunchedEffect(auth) {
        when (auth) {
            AuthState.UNAUTHENTICATED -> navigateToLogin()
            AuthState.AUTHENTICATED_STUDENT -> navigateToStudent()
            AuthState.AUTHENTICATED_TEACHER -> navigateToTeacher()
            else -> {}
        }
    }
}

