package com.kttq.attendassist.core.navigation.student.session_confirm

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.kttq.attendassist.core.data.network.models.Session
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.features.student.session_confirm.SessionConfirmRoute

fun NavController.navigateToSessionConfirm(
    session: Session,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) {
    this.navigate(
        Destination.Student.SessionConfirm(session.sessionId),
        navOptions
    )
}

fun NavGraphBuilder.studentSessionConfirm(
) {
    composable<Destination.Student.SessionConfirm> {
        SessionConfirmRoute()
    }
}