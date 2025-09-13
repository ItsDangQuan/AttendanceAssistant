package com.kttq.attendassist.core.navigation.teacher.class_current_session

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_current_session.ClassCurrentSessionRoute

fun NavController.navigateToCurrentSession(
    sessionId: Int,
    navOptions: NavOptions = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.ClassCurrentSession(sessionId), navOptions)
}

fun NavGraphBuilder.teacherCurrentSession(
    onNavigateBack: () -> Unit
) {
    composable<Destination.Teacher.ClassCurrentSession> {
        ClassCurrentSessionRoute(
            onNavigateBack = onNavigateBack
        )
    }
}