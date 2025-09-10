package com.kttq.attendassist.core.navigation.student.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.profile.StudentProfileRoute

fun NavController.navigateToStudentProfile(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Student.Profile)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.Profile, navOptions)
}

fun NavGraphBuilder.studentProfile(
    navigateToRedirect: () -> Unit
) {
    composable<Destination.Student.Profile> {
        StudentProfileRoute(
            navigateToRedirect = navigateToRedirect
        )
    }
}