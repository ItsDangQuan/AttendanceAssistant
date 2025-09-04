package com.kttq.attendassist.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
sealed class Destination {
    @Serializable
    object Redirect : Destination()

    @Serializable
    object Login : Destination()

    @Serializable
    object Student : Destination() {
        @Serializable
        object Home : Destination()
    }

    @Serializable
    object Teacher : Destination() {

        @Serializable
        object Home : Destination()
    }
}

data class UiMeta(
    val isTopLevel: Boolean = false,
    val showNavigation: Boolean = true,
    val showTopBar: Boolean = true,
    @field:DrawableRes @param:DrawableRes val selectedIconRes: Int? = null,
    @field:DrawableRes @param:DrawableRes val unselectedIconRes: Int? = null,
    @field:StringRes @param:StringRes val iconTextRes: Int? = null,
    @field:StringRes @param:StringRes val titleTextRes: Int? = null,
)

val uiMetaRegistry: Map<KClass<out Destination>, UiMeta> = mapOf(
    Destination.Redirect::class to UiMeta(
        isTopLevel = false,
        showNavigation = false,
        showTopBar = false
    ),

    Destination.Login::class to UiMeta(
        isTopLevel = true,
        showNavigation = false,
        showTopBar = false
    ),

    Destination.Student.Home::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true
    ),

    Destination.Teacher.Home::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true
    )
)

// OLD CODE KEEP FOR REFERENCES

//enum class Destination(
//    val route: String,
//    val isTopLevel: Boolean,
//    val showNavigation: Boolean,
//    val showTopBar: Boolean,
//    @param:DrawableRes @field:DrawableRes val selectedIconRes: Int? = null,
//    @param:DrawableRes @field:DrawableRes val unselectedIconRes: Int? = null,
//    @param:StringRes @field:StringRes val iconTextRes: Int? = null,
//    @param:StringRes @field:StringRes val titleTextRes: Int? = null,
//) {
//    LOGIN(
//        route = loginNavigationRoute,
//        isTopLevel = true,
//        showNavigation = false,
//        showTopBar = false,
//    )
//}

//@Serializable
//sealed class Destination(
//    val isTopLevel: Boolean,
//    val showNavigation: Boolean,
//    val showTopBar: Boolean,
//    @param:DrawableRes @field:DrawableRes val selectedIconRes: Int? = null,
//    @param:DrawableRes @field:DrawableRes val unselectedIconRes: Int? = null,
//    @param:StringRes @field:StringRes val iconTextRes: Int? = null,
//    @param:StringRes @field:StringRes val titleTextRes: Int? = null,
//) {
//    @Serializable
//    object Redirect : Destination(
//        isTopLevel = false,
//        showNavigation = false,
//        showTopBar = false
//    )
//
//    @Serializable
//    object Login : Destination(
//        isTopLevel = true,
//        showNavigation = false,
//        showTopBar = false
//    )
//}