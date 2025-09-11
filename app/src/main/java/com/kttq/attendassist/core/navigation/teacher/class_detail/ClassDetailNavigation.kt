package com.kttq.attendassist.core.navigation.teacher.class_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_detail.ClassDetailRoute


fun NavController.navigateToClassDetail(
    classId: String,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.ClassDetail(classId), navOptions)
}

fun NavGraphBuilder.teacherClassDetail(
    navigateToSessionDetail: (String) -> Unit,
    navigateToStudentDetail: (String) -> Unit
) {
    composable<Destination.Teacher.ClassDetail> {
        ClassDetailRoute(
            navigateToSessionDetail = navigateToSessionDetail,
            navigateToStudentDetail = navigateToStudentDetail,
        )
    }
}