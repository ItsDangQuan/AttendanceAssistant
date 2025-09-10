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
        object Graph : Student() // Changed to inherit from Student

        @Serializable
        object Home : Student()

        @Serializable
        object Profile : Student()

        @Serializable
        object ClassList: Student()

        @Serializable
        data class SessionConfirm(
            val sessionId: String,
        ) : Student()

        data class ClassSummary(
            val classId: String,
        ) : Student()
    }

    @Serializable
    sealed class Teacher : Destination() {
        @Serializable
        @Subgraph
        object Graph : Teacher() // Changed to inherit from Teacher

        @Serializable
        object Home : Teacher()

        @Serializable
        object Profile : Teacher()

        @Serializable
        object ClassList: Teacher()

        @Serializable
        data class ClassDetail(
            val classId: String,
        ) : Teacher()

        @Serializable
        data class ClassStudentList(
            val classId: String,
        ) : Teacher()

        @Serializable
        data class ClassPastSession(
            val classId: String,
        ) : Teacher()
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


    Destination.Student.Profile::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_account_circle,
        unselectedIconRes = R.drawable.ic_account_circle
    ),


    Destination.Student.ClassList::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_view_kanban,
        unselectedIconRes = R.drawable.ic_view_kanban
    ),

    Destination.Teacher.Home::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_home,
        unselectedIconRes = R.drawable.ic_home,
        titleTextRes = R.string.home
    ),

    Destination.Teacher.Profile::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_account_circle,
        unselectedIconRes = R.drawable.ic_account_circle
    ),


    Destination.Teacher.ClassList::class to UiMeta(
        isTopLevel = true,
        showNavigation = true,
        showTopBar = true,
        selectedIconRes = R.drawable.ic_view_kanban,
        unselectedIconRes = R.drawable.ic_view_kanban
    ),

)

fun Destination.uiMeta(): UiMeta = uiMetaRegistry[this::class] ?: UiMeta()

// TODO: Using these for testing. These should be moved to somewhere in the future.
val TopLevelTeacherDest: List<Destination> = listOf(
    Destination.Teacher.Home,
    Destination.Teacher.ClassList,
    Destination.Teacher.Profile
)
val TopLevelStudentDest: List<Destination> = listOf(
    Destination.Student.Home,
    Destination.Student.ClassList,
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
