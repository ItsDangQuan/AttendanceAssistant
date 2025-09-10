package com.kttq.attendassist.core.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.TopLevelStudentDest
import com.kttq.attendassist.core.navigation.TopLevelTeacherDest
import com.kttq.attendassist.core.navigation.login.navigateToLogin
import com.kttq.attendassist.core.navigation.redirect.navigateToRedirect
import com.kttq.attendassist.core.navigation.student.class_list.navigateToStudentClassList
import com.kttq.attendassist.core.navigation.student.home.navigateToStudentHome
import com.kttq.attendassist.core.navigation.student.navigateToStudent
import com.kttq.attendassist.core.navigation.student.profile.navigateToStudentProfile
import com.kttq.attendassist.core.navigation.teacher.home.navigateToTeacherHome
import com.kttq.attendassist.core.navigation.teacher.navigateToTeacher
import com.kttq.attendassist.core.navigation.teacher.profile.navigateToTeacherProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope()
): AppState {
    return remember(
        navController,
        snackbarHostState,
        coroutineScope
    ) {
        AppState(
            navController = navController,
            snackbarHostState = snackbarHostState,
            coroutineScope = coroutineScope
        )
    }
}

class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val coroutineScope: CoroutineScope
) {
    fun onBackClick() {
        navController.popBackStack()
    }

    fun navigate(dest: Destination) {
        when (dest) {
            is Destination.Login -> navController.navigateToLogin()
            is Destination.Redirect -> navController.navigateToRedirect()
            is Destination.Student.Graph -> navController.navigateToStudent()
            is Destination.Student.Home -> navController.navigateToStudentHome()
            is Destination.Student.Profile -> navController.navigateToStudentProfile()
            is Destination.Student.ClassList -> navController.navigateToStudentClassList()

            is Destination.Teacher.Graph -> navController.navigateToTeacher()
            is Destination.Teacher.Home -> navController.navigateToTeacherHome()
            is Destination.Teacher.Profile -> navController.navigateToTeacherProfile()
            is Destination.Teacher.ClassList -> navController.navigateToStudentClassList()

            else -> {}
        }
    }

    val currentDestination: StateFlow<Destination?> = 
        navController.currentBackStackEntryFlow.map { navBackStackEntry ->
            val route = navBackStackEntry.destination
            Destination.listDestinations().firstOrNull { destination ->
                route.hasRoute(destination::class)
            }
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = navController.currentDestination?.let { route ->
                Destination.listDestinations().firstOrNull { destination ->
                    route.hasRoute(destination::class)
                }
            }
        )

    val currentDestinationSubgraph: StateFlow<Destination?> =
        currentDestination.map { dest -> // Changed to derive from currentDestination
            when (dest) {
                is Destination.Student -> Destination.Student.Graph // Assuming any student dest means student graph for top level
                is Destination.Teacher -> Destination.Teacher.Graph // Assuming any teacher dest means teacher graph for top level
                else -> null
            }
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = currentDestination.value?.let { // Initial value based on currentDestination's initial value
                when (it) {
                    is Destination.Student -> Destination.Student.Graph
                    is Destination.Teacher -> Destination.Teacher.Graph
                    else -> null
                }
            }
        )

    val bottomBarDestinations: StateFlow<List<Destination>> =
        currentDestinationSubgraph.map { currentTopLevel ->
            when (currentTopLevel) {
                is Destination.Student.Graph -> TopLevelStudentDest
                is Destination.Teacher.Graph -> TopLevelTeacherDest
                else -> emptyList()
            }
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList() // Initial value can be refined if needed based on topLevelDestination's initial
        )
}
