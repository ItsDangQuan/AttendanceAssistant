package com.kttq.attendassist.core.navigation.teacher

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.home.TeacherHomeRoute

fun NavController.navigateToTeacher(
    navOptions: NavOptions? = null
) {
    this.navigate(Destination.Teacher.Graph, navOptions)
}

fun NavGraphBuilder.teacherNavigation(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit
) {
    navigation<Destination.Teacher.Graph>(
        startDestination = Destination.Teacher.Home
    ) {
        composable<Destination.Teacher.Home> {
            TeacherHomeRoute(onBackClick)
        }
    }

}