package com.kttq.attendassist.core.navigation.teacher.lesson_info

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.lesson_info.TeacherLessonInfoRoute

fun NavController.navigateToTeacherLessonInfo(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Teacher.LessonInfo)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.LessonInfo, navOptions)
}

fun NavGraphBuilder.teacherLessonInfo(
) {
    composable<Destination.Teacher.LessonInfo> {
        TeacherLessonInfoRoute()
    }
}