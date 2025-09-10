package com.kttq.attendassist.core.navigation.teacher.class_student_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_student_list.ClassStudentListRoute

fun NavController.navigateToTeacherClassStudentList(
    cla: Class,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) {
    this.navigate(
        Destination.Teacher.ClassStudentList(cla.classId),
        navOptions
    )
}

fun NavGraphBuilder.teacherClassStudentList(
) {
    composable<Destination.Teacher.ClassStudentList>{
        ClassStudentListRoute()
    }
}