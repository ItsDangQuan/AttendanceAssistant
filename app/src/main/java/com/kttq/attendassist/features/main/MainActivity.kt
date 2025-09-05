package com.kttq.attendassist.features.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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

        splashScreen.setKeepOnScreenCondition {
            !viewModel.ready
        }

        setContent {
            // Noting here: authState collect the value only once, while first created.
            // As nothing touch this, it will not recollect again
            // Since the function getAuthState is cold flow.

            LaunchedEffect(Unit) {
                viewModel.sync()
                viewModel.ready = true
            }

            if (viewModel.ready) {
                AttendanceAssistantTheme {
                    App(
                        appState = rememberAppState(),
                        onSentToBack = {
                            this.moveTaskToBack(true)
                        }
                    )
                }
            }
        }
    }
}
