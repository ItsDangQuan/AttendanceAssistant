package com.kttq.attendassist.core.navigation.student.summary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.summary.StudentSummaryRoute

fun NavController.navigateToStudentSummary(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Student.Summary)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.Summary, navOptions)
}

fun NavGraphBuilder.studentSummary(
) {
    composable<Destination.Student.Summary> {
        StudentSummaryRoute()
    }
}