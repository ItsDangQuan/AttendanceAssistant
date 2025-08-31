package com.kttq.attendassist.features.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.core.domain.model.AuthState
import com.kttq.attendassist.core.navigation.login.loginNavigationRoute
import com.kttq.attendassist.core.ui.App
import com.kttq.attendassist.core.ui.rememberAppState
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        var ready by mutableStateOf(false)

        splashScreen.setKeepOnScreenCondition {
            !ready
        }

        setContent {
            // Noting here: authState collect the value only once, while first created.
            // As nothing touch this, it will not recollect again
            // Since the function getAuthState is cold flow.
            var startDestination by remember { mutableStateOf<String?>(null) }
            val authState by viewModel.authState.collectAsStateWithLifecycle()
            LaunchedEffect(authState) {
                Log.d("Login", "LaunchedEffect restarted with state = $authState")
                when (authState) {
                    AuthState.UNAUTHENTICATED -> {
                        startDestination = loginNavigationRoute
                        ready = true
                    }

                    AuthState.AUTHENTICATED_STUDENT -> {
                        TODO("Add destination here")
                    }

                    AuthState.AUTHENTICATED_TEACHER -> {
                        TODO("Add destination here")
                    }

                    AuthState.LOADING_ROLE -> {}
                }
            }

            if (ready && startDestination != null) {
                AttendanceAssistantTheme {
                    App(
                        appState = rememberAppState(),
                        startDestination = startDestination!!
                    )
                }
            }


        }
    }
}
