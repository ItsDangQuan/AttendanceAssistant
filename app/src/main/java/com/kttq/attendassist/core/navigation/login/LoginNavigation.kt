package com.kttq.attendassist.core.navigation.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.login.LoginRoute

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    this.navigate(Destination.Login, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToStudentHome: () -> Unit,
    navigateToTeacherHome: () -> Unit,
) {
    composable<Destination.Login> {
        // Defined for each features.
        // The routes handle how traffic is directed.
        // This allow for DI, as LoginRoute can be injected with View Model.
        LoginRoute(onShowSnackbar)
    }
}