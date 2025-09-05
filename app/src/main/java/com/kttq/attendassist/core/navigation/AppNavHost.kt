package com.kttq.attendassist.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kttq.attendassist.core.navigation.login.loginScreen
import com.kttq.attendassist.core.navigation.login.navigateToLogin
import com.kttq.attendassist.core.navigation.redirect.navigateToRedirect
import com.kttq.attendassist.core.navigation.redirect.redirect
import com.kttq.attendassist.core.navigation.student.navigateToStudent
import com.kttq.attendassist.core.navigation.student.studentNavigation
import com.kttq.attendassist.core.navigation.teacher.navigateToTeacher
import com.kttq.attendassist.core.navigation.teacher.teacherNavigation

@Composable
fun AppNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    onSentToBack: () -> Unit,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // TODO: Implementing `navigateToRedirect`
        redirect(
            navController::navigateToLogin,
            navController::navigateToStudent,
            navController::navigateToTeacher
        )
        loginScreen(onShowSnackbar, navController::navigateToRedirect)
        teacherNavigation(onShowSnackbar, onBackClick)
        studentNavigation(onSentToBack)
    }
}