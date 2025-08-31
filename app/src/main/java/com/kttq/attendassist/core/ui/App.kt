package com.kttq.attendassist.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kttq.attendassist.core.navigation.AppNavHost
import com.kttq.attendassist.core.navigation.login.loginNavigationRoute

@Composable
fun App(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        bottomBar = { /* TODO: Adding the bottom bar later */},
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.exclude(
                        WindowInsets.ime,
                    ),
                ),
            )
        }
    ) { padding ->
        AppNavHost(
            navController = appState.navController,
            onShowSnackbar = { message, action ->
                appState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = SnackbarDuration.Short
                ) == SnackbarResult.ActionPerformed
            },
            onBackClick = {}, // I do not understand what is he going to do with this
            startDestination = loginNavigationRoute, // Do not ask me, he said that
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .systemBarsPadding()
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    }
}