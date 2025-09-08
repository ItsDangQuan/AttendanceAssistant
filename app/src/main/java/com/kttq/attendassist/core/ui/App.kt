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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import com.kttq.attendassist.R
import com.kttq.attendassist.core.navigation.AppNavHost
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.TopLevelStudentDest
import com.kttq.attendassist.core.navigation.TopLevelTeacherDest
import com.kttq.attendassist.core.navigation.uiMeta
import com.kttq.attendassist.core.ui.components.AppBottomBar
import com.kttq.attendassist.core.ui.components.AppCenterAlignedTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    appState: AppState,
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            val currentDest = appState.currentDestinationObjectAsState
            if (currentDest != null) {
                val uiMeta = currentDest.uiMeta()
                if (uiMeta.showTopBar) {
                    AppCenterAlignedTopBar(
                        title = if (uiMeta.titleTextRes != null) {
                            stringResource(uiMeta.titleTextRes)
                        } else {
                            null
                        },
                        navigationIconRes = R.drawable.ic_arrow_back
                    )
                }
            }
        },
        bottomBar = {
            val currentDest = appState.currentDestinationObjectAsState
            if (currentDest != null) {
                val uiMeta = currentDest.uiMeta()
                if (uiMeta.showNavigation) {
                    when(currentDest) {
                         is Destination.Student-> AppBottomBar(
                            TopLevelStudentDest,
                            isSelected = {
                                appState.navController.currentDestination?.hasRoute(it::class) == true
                            },
                            onDestinationSelected = {
                                appState.navigate(it)
                            }
                        )

                        is Destination.Teacher -> AppBottomBar(
                            TopLevelTeacherDest,
                            isSelected = {
                                appState.navController.currentDestination?.hasRoute(it::class) == true
                            },
                            onDestinationSelected = {
                                appState.navigate(it)
                            }
                        )

                        else -> {}
                    }
                }
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
            onSentToBack = onSentToBack,
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