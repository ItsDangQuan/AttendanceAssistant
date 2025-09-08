package com.kttq.attendassist.core.navigation.student

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.student.home.studentHome
import com.kttq.attendassist.core.navigation.student.lesson_info.studentLessonInfo
import com.kttq.attendassist.core.navigation.student.profile.studentProfile
import com.kttq.attendassist.core.navigation.student.summary.studentSummary

fun NavController.navigateToStudent(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Redirect)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.Graph, navOptions)
}

fun NavGraphBuilder.studentNavigation(
    onSentToBack: () -> Unit
) {
    navigation<Destination.Student.Graph>(
        startDestination = Destination.Student.Home
    ) {
        studentHome(onSentToBack)
        studentProfile()
        studentLessonInfo()
        studentSummary()
    }
}