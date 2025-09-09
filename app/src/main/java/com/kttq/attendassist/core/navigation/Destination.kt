package com.kttq.attendassist.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.kttq.attendassist.R
import kotlinx.serialization.Serializable
import javax.inject.Singleton
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subgraph

@Serializable
@Singleton
sealed class Destination {
    companion object {
        private fun collectObjects(kClass: KClass<out Destination>): List<Destination> {
            kClass.objectInstance?.let { return listOf(it) }
            return kClass.sealedSubclasses.flatMap { collectObjects(it) }
        }

        fun listDestinations(): List<Destination> =
            Destination::class.sealedSubclasses
                .flatMap { collectObjects(it) }
                .filter { it::class.findAnnotation<Subgraph>() == null }
    }

    @Serializable
    object Redirect : Destination()

    @Serializable
    object Login : Destination()

    @Serializable
    sealed class Student : Destination() {
        @Serializable
        @Subgraph
        object Graph : Destination()

        @Serializable
        object Home : Student()

        @Serializable
        object LessonInfo : Student()

        @Serializable
        object Profile : Student()

        @Serializable
        object Summary : Student()
    }

    @Serializable
    sealed class Teacher : Destination() {
        @Serializable
        @Subgraph
        object Graph : Destination()

        @Serializable
        object Home : Teacher()

        @Serializable
        object LessonInfo : Teacher()

        @Serializable
        object Profile : Teacher()
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
        showTopBar = true,
        selectedIconRes = R.drawable.ic_home,
        unselectedIconRes = R.drawable.ic_home,
        titleTextRes = R.string.home
    ),

    Destination.Student.LessonInfo::class to UiMeta(
        isTopLevel = false,
        showNavigation = false,
        showTopBar = true
    ),

    Destination.Student.Profile::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_account_circle,
        unselectedIconRes = R.drawable.ic_account_circle
    ),

    Destination.Student.Summary::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_analytics,
        unselectedIconRes = R.drawable.ic_analytics
    ),

    Destination.Teacher.Home::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_home,
        unselectedIconRes = R.drawable.ic_home,
        titleTextRes = R.string.home
    ),

    Destination.Teacher.LessonInfo::class to UiMeta(
        isTopLevel = false,
        showNavigation = false,
        showTopBar = true
    ),

    Destination.Teacher.Profile::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_account_circle,
        unselectedIconRes = R.drawable.ic_account_circle
    ),
)

fun Destination.uiMeta(): UiMeta = uiMetaRegistry[this::class] ?: UiMeta()

// TODO: Using these for testing. These should be moved to somewhere in the future.
val TopLevelTeacherDest = listOf(
    Destination.Teacher.Home,
    Destination.Teacher.Profile
)
val TopLevelStudentDest = listOf(
    Destination.Student.Home,
    Destination.Student.Summary,
    Destination.Student.Profile
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