package com.kttq.attendassist.core.navigation.teacher.class_session_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_session_list.ClassSessionListRoute

fun NavController.navigateToTeacherSessionList(
    classId: Int,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.ClassSessionList(classId), navOptions)
}

fun NavGraphBuilder.teacherSessionList(
) {
    composable<Destination.Teacher.ClassSessionList> {
        ClassSessionListRoute()
    }
}