package com.kttq.attendassist.core.navigation.teacher.class_pass_session

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_past_session.ClassPastSessionRoute

fun NavController.navigateToClassPastSession(
    classId: String,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(
        Destination.Teacher.ClassPastSession(classId),
        navOptions
    )
}

fun NavGraphBuilder.teacherClassPastSession(
) {
    composable<Destination.Teacher.ClassPastSession>{
        ClassPastSessionRoute()
    }
}