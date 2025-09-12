package com.kttq.attendassist.core.navigation.student.class_summary

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.class_summary.ClassSummaryRoute

fun NavController.navigateToClassSummary(
    classId: String,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) {
    this.navigate(
        Destination.Student.ClassSummary(classId),
        navOptions
    )
}

fun NavGraphBuilder.studentClassSummary(
    onShowSnackbar: suspend (String, String?) -> Boolean
) {
    composable<Destination.Student.ClassSummary> {
        ClassSummaryRoute(onShowSnackbar)
    }
}