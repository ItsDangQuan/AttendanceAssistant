package com.kttq.attendassist.core.navigation.teacher.class_detail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_detail.ClassDetailRoute


fun NavController.navigateToClassDetail(
    classId: Int,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.ClassDetail(classId), navOptions)
}

fun NavGraphBuilder.teacherClassDetail(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSessionDetail: (Int) -> Unit,
    navigateToStudentDetail: (Int) -> Unit,
    navigateToCurrentSession: (Int) -> Unit,
) {
    composable<Destination.Teacher.ClassDetail> {
        ClassDetailRoute(
            onShowSnackbar = onShowSnackbar,
            navigateToSessionDetail = navigateToSessionDetail,
            navigateToStudentDetail = navigateToStudentDetail,
            navigateToCurrentSession = navigateToCurrentSession
        )
    }
}