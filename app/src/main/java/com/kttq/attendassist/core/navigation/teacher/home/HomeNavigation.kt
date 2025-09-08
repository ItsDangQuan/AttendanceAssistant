package com.kttq.attendassist.core.navigation.teacher.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.home.TeacherHomeRoute

fun NavController.navigateToTeacherHome(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Teacher.Home)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.Home, navOptions)
}

fun NavGraphBuilder.teacherHome(
    onSentToBack: () -> Unit
) {
    composable<Destination.Teacher.Home> {
        TeacherHomeRoute(onSentToBack)
    }
}