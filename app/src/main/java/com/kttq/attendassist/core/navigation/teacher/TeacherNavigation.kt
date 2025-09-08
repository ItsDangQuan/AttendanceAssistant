package com.kttq.attendassist.core.navigation.teacher

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.teacher.home.teacherHome
import com.kttq.attendassist.core.navigation.teacher.lesson_info.teacherLessonInfo
import com.kttq.attendassist.core.navigation.teacher.profile.teacherProfile

fun NavController.navigateToTeacher(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Teacher.Home)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Teacher.Graph, navOptions)
}

fun NavGraphBuilder.teacherNavigation(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onSentToBack: () -> Unit
) {
    navigation<Destination.Teacher.Graph>(
        startDestination = Destination.Teacher.Home
    ) {
        teacherHome(onSentToBack)
        teacherLessonInfo()
        teacherProfile()
    }
}