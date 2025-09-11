package com.kttq.attendassist.core.navigation.teacher

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.teacher.class_detail.teacherClassDetail
import com.kttq.attendassist.core.navigation.teacher.class_pass_session.teacherClassPastSession
import com.kttq.attendassist.core.navigation.teacher.class_student_list.teacherClassStudentList
import com.kttq.attendassist.core.navigation.teacher.home.teacherHome
import com.kttq.attendassist.core.navigation.teacher.profile.teacherProfile
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.navigation.teacher.class_list.teacherClassList

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
    onSentToBack: () -> Unit,
    navigationToClassDetail: (String) -> Unit,
    navigateToSessionDetail: (String) -> Unit,
    navigateToStudentDetail: (String) -> Unit,
    navigateToRedirect: () -> Unit
) {
    navigation<Destination.Teacher.Graph>(
        startDestination = Destination.Teacher.Home
    ) {
        teacherHome(
            onSentToBack,
            navigateToSessionDetail,
        )
        teacherProfile(
            navigateToRedirect = navigateToRedirect
        )
        teacherClassList(navigationToClassDetail)
        teacherClassDetail(
            navigateToSessionDetail = navigateToSessionDetail,
            navigateToStudentDetail = navigateToStudentDetail,
        )
        teacherClassPastSession()
        teacherClassStudentList()
    }
}