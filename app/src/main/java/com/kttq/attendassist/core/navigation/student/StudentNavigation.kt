package com.kttq.attendassist.core.navigation.student

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.home.StudentHomeRoute

fun NavController.navigateToStudent(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Student.Graph)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.Graph, navOptions)
}

fun NavGraphBuilder.studentNavigation(
    onSentToBack: () -> Unit
) {
    navigation<Destination.Student.Graph>(
        startDestination = Destination.Student.Home
    ) {
        composable<Destination.Student.Home> {
            StudentHomeRoute(onSentToBack)
        }
    }
}