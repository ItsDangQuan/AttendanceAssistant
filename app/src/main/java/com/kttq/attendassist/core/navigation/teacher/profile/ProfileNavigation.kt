package com.kttq.attendassist.core.navigation.teacher.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.profile.TeacherProfileRoute

fun NavController.navigateToTeacherProfile(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Teacher.Profile)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.Profile, navOptions)
}

fun NavGraphBuilder.teacherProfile(
    navigateToRedirect: () -> Unit
) {
    composable<Destination.Teacher.Profile> {
        TeacherProfileRoute(
            navigateToRedirect = navigateToRedirect
        )
    }
}