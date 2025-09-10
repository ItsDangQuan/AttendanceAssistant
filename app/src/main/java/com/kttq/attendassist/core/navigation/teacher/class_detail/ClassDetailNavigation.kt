package com.kttq.attendassist.core.navigation.teacher.class_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.data.network.models.Class
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_detail.ClassDetailRoute


fun NavController.navigateToClassDetail(
    cla: Class,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.ClassDetail(cla.classId))
}

fun NavGraphBuilder.teacherClassDetail(
    navigateToSessionDetail: (Class) -> Unit,
    navigateToStudentDetail: (Class) -> Unit
) {
    composable<Destination.Teacher.ClassDetail> {
        ClassDetailRoute(
            navigateToSessionDetail = navigateToSessionDetail,
            navigateToStudentDetail = navigateToStudentDetail,
        )
    }
}