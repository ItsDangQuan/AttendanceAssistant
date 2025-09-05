package com.kttq.attendassist.core.navigation.student

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.home.StudentHomeRoute

fun NavController.navigateToStudent(
    navOptions: NavOptions? = null
) {
    this.navigate(Destination.Student.Graph, navOptions)
}

fun NavGraphBuilder.studentNavigation(

) {
    navigation<Destination.Student.Graph>(
        startDestination = Destination.Student.Home
    ) {
        composable<Destination.Student.Home> {
            StudentHomeRoute()
        }
    }
}