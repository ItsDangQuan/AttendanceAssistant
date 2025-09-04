package com.kttq.attendassist.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kttq.attendassist.core.navigation.login.loginScreen
import com.kttq.attendassist.core.navigation.teacher.teacherNavigation

@Composable
fun AppNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // TODO: Implementing `navigateToRedirect`
        loginScreen(onShowSnackbar, {})
        teacherNavigation(onShowSnackbar,onBackClick)
    }
}