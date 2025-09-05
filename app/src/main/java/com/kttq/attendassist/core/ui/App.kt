package com.kttq.attendassist.core.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import com.kttq.attendassist.core.navigation.AppNavHost
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.TopLevelStudentDest
import com.kttq.attendassist.core.navigation.TopLevelTeacherDest
import com.kttq.attendassist.core.ui.components.AppBottomBar

@Composable
fun App(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            when {
                appState.isTeacher() -> AppBottomBar(
                    TopLevelStudentDest,
                    Destination.Teacher.Home,
                    onDestinationSelected = {}
                )
                appState.isStudent() -> AppBottomBar(
                    TopLevelTeacherDest,
                    Destination.Student.Home,
                    onDestinationSelected = {}
                )
                else -> {}
            }
        },
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
                    duration = if (action != null) SnackbarDuration.Short else SnackbarDuration.Indefinite
                ) == SnackbarResult.ActionPerformed
            },
            onBackClick = appState::onBackClick,
            startDestination = Destination.Redirect,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .systemBarsPadding()
                .statusBarsPadding()
                .navigationBarsPadding()
        )
    }
}