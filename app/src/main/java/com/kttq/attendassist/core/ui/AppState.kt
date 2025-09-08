package com.kttq.attendassist.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.login.navigateToLogin
import com.kttq.attendassist.core.navigation.redirect.navigateToRedirect
import com.kttq.attendassist.core.navigation.student.home.navigateToStudentHome
import com.kttq.attendassist.core.navigation.student.lesson_info.navigateToStudentLessonInfo
import com.kttq.attendassist.core.navigation.student.navigateToStudent
import com.kttq.attendassist.core.navigation.student.profile.navigateToStudentProfile
import com.kttq.attendassist.core.navigation.student.summary.navigateToStudentSummary
import com.kttq.attendassist.core.navigation.teacher.home.navigateToTeacherHome
import com.kttq.attendassist.core.navigation.teacher.lesson_info.navigateToTeacherLessonInfo
import com.kttq.attendassist.core.navigation.teacher.navigateToTeacher
import com.kttq.attendassist.core.navigation.teacher.profile.navigateToTeacherProfile

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): AppState {
    return remember(
        navController,
        snackbarHostState
    ) {
        AppState(
            navController = navController,
            snackbarHostState = snackbarHostState
        )
    }
}

class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState
) {
    fun onBackClick() {
        navController.popBackStack()
    }

    val currentDestinationAsState: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

//    @Composable
//    fun isTeacher(): Boolean =
//        currentDestinationAsState?.hierarchy?.any {
//            it.hasRoute(Destination.Teacher.Graph::class)
//        } == true
//
//    @Composable
//    fun isStudent(): Boolean =
//        currentDestinationAsState?.hierarchy?.any {
//            it.hasRoute(Destination.Student.Graph::class)
//        } == true

    val currentDestinationObjectAsState: Destination?
        @Composable get() = Destination.listDestinations().firstOrNull {
            currentDestinationAsState?.hasRoute(it::class) == true
        }

    fun navigate(dest: Destination) {
        when (dest) {
            is Destination.Login -> navController.navigateToLogin()

            is Destination.Redirect -> navController.navigateToRedirect()

            is Destination.Student.Graph -> navController.navigateToStudent()
            is Destination.Student.Home -> navController.navigateToStudentHome()
            is Destination.Student.LessonInfo -> navController.navigateToStudentLessonInfo()
            is Destination.Student.Profile -> navController.navigateToStudentProfile()
            is Destination.Student.Summary -> navController.navigateToStudentSummary()

            is Destination.Teacher.Graph -> navController.navigateToTeacher()
            is Destination.Teacher.Home -> navController.navigateToTeacherHome()
            is Destination.Teacher.LessonInfo -> navController.navigateToTeacherLessonInfo()
            is Destination.Teacher.Profile -> navController.navigateToTeacherProfile()
        }
    }
}