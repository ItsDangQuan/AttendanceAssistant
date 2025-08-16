package com.kttq.attendassist.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kttq.attendassist.core.navigation.login.loginNavigationRoute

enum class Destination(
    val route: String,
    val isTopLevel: Boolean,
    val showNavigation: Boolean,
    val showTopBar: Boolean,
    @param:DrawableRes @field:DrawableRes val selectedIconRes: Int? = null,
    @param:DrawableRes @field:DrawableRes val unselectedIconRes: Int? = null,
    @param:StringRes @field:StringRes val iconTextRes: Int? = null,
    @param:StringRes @field:StringRes val titleTextRes: Int? = null,
) {
    LOGIN(
        route = loginNavigationRoute,
        isTopLevel = true,
        showNavigation = false,
        showTopBar = false,
    )
}