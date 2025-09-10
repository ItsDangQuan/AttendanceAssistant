package com.kttq.attendassist.core.navigation.student.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.data.network.responses.Session
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.home.StudentHomeRoute

fun NavController.navigateToStudentHome(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Student.Home)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.Home, navOptions)
}

fun NavGraphBuilder.studentHome(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onSentToBack: () -> Unit,
    navigateToNewSession: (Session) -> Unit
) {
    composable<Destination.Student.Home> {
        StudentHomeRoute(
            onSentToBack,
            onShowSnackbar,
            navigateToNewSession
        )
    }
}