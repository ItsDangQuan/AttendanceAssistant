package com.kttq.attendassist.core.navigation.student

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.kttq.attendassist.core.model.Session
import com.kttq.attendassist.core.navigation.Destination
import com.kttq.attendassist.core.navigation.student.class_list.studentClassList
import com.kttq.attendassist.core.navigation.student.home.studentHome
import com.kttq.attendassist.core.navigation.student.profile.studentProfile
import com.kttq.attendassist.core.navigation.student.session_confirm.studentSessionConfirm

fun NavController.navigateToStudent(
    navOptions: NavOptions? = navOptions {
        popUpTo(Destination.Redirect)
        launchSingleTop = true
    }
) {
    this.navigate(Destination.Student.Graph, navOptions)
}

fun NavGraphBuilder.studentNavigation(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onSentToBack: () -> Unit,
    navigateToNewSession: (String) -> Unit,
    navigateToRedirect: () -> Unit
) {
    navigation<Destination.Student.Graph>(
        startDestination = Destination.Student.Home
    ) {
        studentHome(onShowSnackbar, onSentToBack, navigateToNewSession)
        studentProfile(navigateToRedirect)
        studentClassList()
        studentSessionConfirm()
    }
}