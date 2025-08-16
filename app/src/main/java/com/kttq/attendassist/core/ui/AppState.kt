package com.kttq.attendassist.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
   navController: NavHostController = rememberNavController()
): AppState {
    return remember(
        navController
    ) {
        AppState(
            navController = navController
        )
    }
}
class AppState (
    val navController: NavHostController
) {
    fun onBackClick() {
        navController.popBackStack()
    }
    val currentDestinationAsState: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

}