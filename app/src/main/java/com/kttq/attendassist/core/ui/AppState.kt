package com.kttq.attendassist.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.login.navigateToLogin
import com.kttq.attendassist.core.navigation.redirect.navigateToRedirect
import com.kttq.attendassist.core.navigation.student.navigateToStudent
import com.kttq.attendassist.core.navigation.teacher.navigateToTeacher

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

    @Composable
    fun isTeacher(): Boolean =
        currentDestinationAsState?.hierarchy?.any {
            it.hasRoute(Destination.Teacher.Graph::class)
        } == true

    @Composable
    fun isStudent(): Boolean =
        currentDestinationAsState?.hierarchy?.any {
            it.hasRoute(Destination.Student.Graph::class)
        } == true

    val currentDestinationObjectAsState: Destination?
        @Composable get() = Destination.listDestinations().firstOrNull {
            currentDestinationAsState?.hasRoute(it::class) == true
        }

    fun navigate(dest: Destination) {
        when (dest) {
            is Destination.Login -> navController.navigateToLogin()
            is Destination.Redirect -> navController.navigateToRedirect()
            is Destination.Student.Graph -> navController.navigateToStudent()
            is Destination.Student.Home -> TODO("Navigate to Student Home not yet implemented")
            is Destination.Teacher.Graph -> navController.navigateToTeacher()
            is Destination.Teacher.Home -> TODO("Navigate to Teacher Home not yet implemented")
        }
    }
}