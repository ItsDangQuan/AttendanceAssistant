package com.kttq.attendassist.core.navigation.student.lesson_info

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.lesson_info.StudentLessonInfoRoute

fun NavController.navigateToStudentLessonInfo(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Student.LessonInfo)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.LessonInfo, navOptions)
}

fun NavGraphBuilder.studentLessonInfo(
) {
    composable<Destination.Student.LessonInfo> {
        StudentLessonInfoRoute()
    }
}