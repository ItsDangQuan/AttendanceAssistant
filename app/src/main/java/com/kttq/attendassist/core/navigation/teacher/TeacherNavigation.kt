package com.kttq.attendassist.core.navigation.teacher

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.teacher.class_current_session.teacherCurrentSession
import com.kttq.attendassist.core.navigation.teacher.class_detail.teacherClassDetail
import com.kttq.attendassist.core.navigation.teacher.class_list.teacherClassList
import com.kttq.attendassist.core.navigation.teacher.class_pass_session.teacherClassPastSession
import com.kttq.attendassist.core.navigation.teacher.class_session_list.teacherSessionList
import com.kttq.attendassist.core.navigation.teacher.class_student_list.teacherClassStudentList
import com.kttq.attendassist.core.navigation.teacher.home.teacherHome
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
    onSentToBack: () -> Unit,
    navigationToClassDetail: (Int) -> Unit,
    navigateToSessionDetail: (Int) -> Unit,
    navigateToStudentDetail: (Int) -> Unit,
    navigateToCurrentSession: (Int) -> Unit,
    navigateToSessionList: (Int) -> Unit,
    navigateToRedirect: () -> Unit,
    navigateBack: () -> Unit
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
            onShowSnackbar,
            navigateToSessionDetail = navigateToSessionList,
            navigateToStudentDetail = navigateToStudentDetail,
            navigateToCurrentSession = navigateToCurrentSession
        )
        teacherCurrentSession(navigateBack)
        teacherClassPastSession()

        teacherClassStudentList()
        teacherSessionList(
            navigateToSession = navigateToSessionDetail
        )
    }
}