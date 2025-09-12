package com.kttq.attendassist.core.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kttq.attendassist.core.navigation.login.login
import com.kttq.attendassist.core.navigation.login.navigateToLogin
import com.kttq.attendassist.core.navigation.redirect.navigateToRedirect
import com.kttq.attendassist.core.navigation.redirect.redirect
import com.kttq.attendassist.core.navigation.student.class_summary.navigateToClassSummary
import com.kttq.attendassist.core.navigation.student.navigateToStudent
import com.kttq.attendassist.core.navigation.student.session_confirm.navigateToSessionConfirm
import com.kttq.attendassist.core.navigation.student.studentNavigation
import com.kttq.attendassist.core.navigation.teacher.class_current_session.navigateToCurrentSession
import com.kttq.attendassist.core.navigation.teacher.class_detail.navigateToClassDetail
import com.kttq.attendassist.core.navigation.teacher.class_pass_session.navigateToClassPastSession
import com.kttq.attendassist.core.navigation.teacher.class_student_list.navigateToTeacherClassStudentList
import com.kttq.attendassist.core.navigation.teacher.navigateToTeacher
import com.kttq.attendassist.core.navigation.teacher.teacherNavigation

@Composable
fun AppNavHost(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackClick: () -> Unit,
    onSentToBack: () -> Unit,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    Log.d("Nav", "Current navigation: $startDestination")
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        // TODO: Implementing `navigateToRedirect`
        redirect(
            navController::navigateToLogin,
            navController::navigateToStudent,
            navController::navigateToTeacher
        )
        login(onShowSnackbar, navController::navigateToRedirect)
        teacherNavigation(onShowSnackbar, onSentToBack,
            navController::navigateToClassDetail,
            navController::navigateToClassPastSession,
            navController::navigateToTeacherClassStudentList,
            navController::navigateToCurrentSession,
            navController::navigateToRedirect,
            onBackClick
        )
        studentNavigation(onShowSnackbar, onSentToBack,
            navController::navigateToSessionConfirm,
            navController::navigateToRedirect,
            navController::navigateToClassSummary
        )
    }
}