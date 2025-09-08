package com.kttq.attendassist.features.teacher.lesson_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun TeacherLessonInfoRoute(
    modifier: Modifier = Modifier,
    viewModel: TeacherLessonInfoViewModel = hiltViewModel()
) {
    val lessonDetailsUiState by viewModel.lessonDetailsUiState.collectAsState()
    val attendanceStatsUiState by viewModel.attendanceStatsUiState.collectAsState()
    TeacherLessonInfoScreen(
        modifier = modifier,
        lessonDetailsUiState = lessonDetailsUiState,
        attendanceStatsUiState = attendanceStatsUiState,
        onStopCheckingClicked = viewModel::onStopCheckingClicked,
        onExportClicked = viewModel::onExportClicked
    )
}

@Composable
fun TeacherLessonInfoScreen(
    lessonDetailsUiState: LessonDetailsUiState?,
    attendanceStatsUiState: AttendanceStatsUiState?,
    onStopCheckingClicked: () -> Unit,
    onExportClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            AppSectionTitle(text = "Course Information")
            AppBodyPrimary(text = "Course ID: ${lessonDetailsUiState?.courseId ?: "N/A"}")
            AppBodyPrimary(text = "Course Name: ${lessonDetailsUiState?.courseName ?: "N/A"}")

            AppSectionTitle(text = "Attendance Information")
            AppBodyPrimary(text = "Time: ${lessonDetailsUiState?.time ?: "N/A"}")
            AppBodyPrimary(text = "Date: ${lessonDetailsUiState?.date ?: "N/A"}")
            AppBodyPrimary(text = "Presenter today: ${lessonDetailsUiState?.presenter ?: "N/A"}")

            AppSectionTitle(text = "Statistics")

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                AppCard(
                    modifier = Modifier.weight(1f)
                ) {
                    AppLabelPrimary("Total:")
                    AppLabelSecondary(attendanceStatsUiState?.totalStudents?.toString() ?: "N/A")
                }

                AppCard(
                    modifier = Modifier.weight(1f)
                ) {
                    AppLabelPrimary("Already checked:")
                    AppLabelSecondary(attendanceStatsUiState?.checkedStudents?.toString() ?: "N/A")
                }
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {

            AppButton(
                onClick = onStopCheckingClicked,
                modifier = Modifier.weight(1f)
            ) {
                AppLabelPrimary("Stop checking")
            }

            AppButton(
                onClick = onExportClicked,
                modifier = Modifier.weight(1f)
            ) {
                AppLabelPrimary("Export")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TeacherLessonInfoScreenPreview() {
    AttendanceAssistantTheme {
        TeacherLessonInfoScreen(
            lessonDetailsUiState = LessonDetailsUiState(
                courseId = "PREVIEW101",
                courseName = "Preview Course",
                time = "10:00 AM",
                date = "Jan 1st",
                presenter = "Dr. Preview"
            ),
            attendanceStatsUiState = AttendanceStatsUiState(
                totalStudents = 50,
                checkedStudents = 25
            ),
            onStopCheckingClicked = {},
            onExportClicked = {}
        )
    }
}
