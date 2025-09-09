package com.kttq.attendassist.core.navigation.student.class_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.class_list.StudentClassListRoute
import com.kttq.attendassist.features.student.summary.StudentSummaryRoute

fun NavController.navigateToStudentClassList(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Student.ClassList)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.ClassList, navOptions)
}

fun NavGraphBuilder.studentClassList(
) {
    composable<Destination.Student.ClassList> {
        StudentClassListRoute()
    }
}