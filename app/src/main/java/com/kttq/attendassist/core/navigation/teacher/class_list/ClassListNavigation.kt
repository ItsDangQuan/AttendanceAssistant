package com.kttq.attendassist.core.navigation.teacher.class_list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.teacher.class_list.TeacherClassListRoute
import com.kttq.attendassist.core.model.Class

fun NavController.navigateToTeacherClassList(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Teacher.ClassList)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.ClassList, navOptions)
}

fun NavGraphBuilder.teacherClassList(
    navigateToClass: (Class) -> Unit
) {
    composable<Destination.Teacher.ClassList> {
        TeacherClassListRoute(
            navigateToClass = navigateToClass
        )
    }
}