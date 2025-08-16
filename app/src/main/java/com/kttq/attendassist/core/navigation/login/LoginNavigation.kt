package com.kttq.attendassist.core.navigation.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val loginNavigationRoute = "login_route"

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    this.navigate(loginNavigationRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    navigateToStudentHome: () -> Unit,
    navigateToTeacherHome: () -> Unit,
) {
    composable(route = loginNavigationRoute) {
        // Defined for each features.
        // The routes handle how traffic is directed.
        // This allow for DI, as LoginRoute can be injected with View Model.
        // LoginRoute()
    }
}