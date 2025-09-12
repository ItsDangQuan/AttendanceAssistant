package com.kttq.attendassist.core.ui

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.kttq.attendassist.R
import com.kttq.attendassist.core.navigation.AppNavHost
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.uiMeta
import com.kttq.attendassist.core.ui.components.AppBottomBar
import com.kttq.attendassist.core.ui.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    appState: AppState,
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.d("App", "App recomposed")


    val currentDestination by appState.currentDestination.collectAsState()
    val navigateUpdated by rememberUpdatedState(newValue = { d: Destination -> appState.navigate(d) })

    Scaffold(

        modifier = modifier,
        topBar = {
            if(currentDestination?.uiMeta()?.showTopBar == true) {
                Log.d("App", "Top bar recomposed")
                AppTopBar(
                    title = currentDestination?.uiMeta()?.title,
                    navigationIconRes = R.drawable.ic_arrow_back,
                    onNavigationClick = appState::onBackClick
                )
            }

        },
        bottomBar = {
            // Keep the bar visible by default (empty list = no items)
            if (
                appState.currentDestinationSubgraph.collectAsState().value != null &&
                currentDestination?.uiMeta()?.showNavigation == true
            ) {
                AppBottomBar(
                    destinations = appState.bottomBarDestinations.collectAsState().value,
                    isSelected = { destination ->
                        destination == currentDestination
                    },
                    onDestinationSelected = {
                        navigateUpdated(it)
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.safeDrawing.exclude(WindowInsets.ime),
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

