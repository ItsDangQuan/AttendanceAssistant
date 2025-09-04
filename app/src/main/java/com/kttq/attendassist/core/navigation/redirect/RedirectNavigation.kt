package com.kttq.attendassist.core.navigation.redirect

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.redirect.RedirectRoute

fun NavController.navigateToRedirect(
    navOptions: NavOptions? = null
) {
    this.navigate(Destination.Redirect, navOptions)
}

fun NavGraphBuilder.redirect(
    navigateToLogin: () -> Unit,
    navigateToStudent: () -> Unit,
    navigateToTeacher: () -> Unit
) {
    composable<Destination.Redirect> {
        RedirectRoute(
            navigateToLogin = navigateToLogin,
            navigateToStudent = navigateToStudent,
            navigateToTeacher = navigateToTeacher
        )
    }
}