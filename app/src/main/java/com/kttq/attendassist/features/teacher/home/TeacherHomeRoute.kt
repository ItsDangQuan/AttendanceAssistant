package com.kttq.attendassist.features.teacher.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.StudentSession
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun TeacherHomeRoute(
    onSentToBack: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToNewSession: (StudentSession) -> Unit = {},
    viewModel: TeacherHomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val date by viewModel.formattedDate.collectAsStateWithLifecycle()

    BackHandler {
        onSentToBack()
    }
    // TeacherHomeScreen(
    //     userName = uiState.userName,
    //     modifier = modifier
    // )
    TeacherHomeScreen(
        userName = uiState.userName,
        date = date,
        recentSessions = viewModel.recentSession.collectAsStateWithLifecycle().value,
        navigateToNewSession = navigateToNewSession,
        modifier = modifier
    )
}

@Composable
fun TeacherHomeScreen(
    userName: String,
    date: String,
    recentSessions: List<StudentSession>,
    modifier: Modifier = Modifier,
    navigateToNewSession: (StudentSession) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
    ) {
        AppScreenTitle("Hello, $userName")
        AppSectionTitle(date) // TODO: Maybe refactor
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_large)), // Added vertical padding
            thickness = dimensionResource(R.dimen.divider_height),
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(
                recentSessions.size
            ) {
                AppCard(
                    clickable = true,
                    onClick = {
                        navigateToNewSession(recentSessions[it])
                    }
                ) {
                    AppLabelPrimary(recentSessions[it].classId)
                    AppLabelSecondary(
                        recentSessions[it].courseId + " - " +
                                recentSessions[it].courseName
                    )
                }
            }

        }
    }
}

@Preview
@Composable
private fun TeacherHomeScreenPreview() {
    AttendanceAssistantTheme {
        TeacherHomeScreen(
            userName = "User",
            date = "",
            recentSessions = emptyList()
        )
    }
}
// @Composable
// fun TeacherHomeScreen(
//     userName: String,
//     modifier: Modifier = Modifier,
// ) {
// 
//     Column(
//         modifier = modifier.fillMaxWidth()
//     ) {
//         AppScreenTitle("Hello, $userName")
//         Row(
//             verticalAlignment = Alignment.CenterVertically
//         ) {
//             AppSectionTitle("Current Checking")
//             AppIconButton(
//                 iconId = R.drawable.ic_arrow_forward,
//                 onClick = {
// 
//                 },
//                 modifier = Modifier.wrapContentSize()
//             )
//         }
//         LazyColumn {
//             //TODO: Add list of current class and incoming classes based on uiState
//         }
// 
//         Row(
//             verticalAlignment = Alignment.CenterVertically
//         ) {
//             AppSectionTitle("Class Today")
//             AppIconButton(
//                 iconId = R.drawable.ic_arrow_forward,
//                 onClick = {
// 
//                 },
//                 modifier = Modifier.wrapContentSize()
//             )
//         }
//         LazyColumn {
//             //TODO: Add list of current class and incoming classes based on uiState
//         }
//     }
// }
// 
// @Preview(showBackground = true)
// @Composable
// private fun TeacherHomeScreenPreview() {
//     AttendanceAssistantTheme {
//         // The preview will attempt to use Hilt to create the ViewModel.
//         // For complex ViewModels, this might require additional Hilt setup for previews
//         // or providing a mock ViewModel/UiState directly to TeacherHomeScreen.
//         TeacherHomeScreen(
//             userName = ""
//         )
//     }
// }

