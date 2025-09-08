package com.kttq.attendassist.features.student.lesson_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
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
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppOutlineTextField
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentLessonInfoRoute(
    modifier: Modifier = Modifier,
    viewModel: StudentLessonInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    StudentLessonInfoScreen(
        uiState = uiState,
        changeReasonToAbsent = viewModel::changeReasonToAbsent,
        changeWantToAbsent = viewModel::changeWantToAbsent,
        onSubmit = viewModel::submitAbsenceRequest,
        modifier = modifier
    )
}

@Composable
fun StudentLessonInfoScreen(
    uiState: StudentLessonInfoUiState,
    changeReasonToAbsent: (String) -> Unit,
    changeWantToAbsent: () -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (uiState.errorMessage != null) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Error: ${uiState.errorMessage}")
        }
        return
    }

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
            AppBodyPrimary(text = "Course ID: ${uiState.courseId}")
            AppBodyPrimary(text = "Course Name: ${uiState.courseName}")

            AppSectionTitle(text = "Attendance Information")
            AppBodyPrimary(text = "Time: ${uiState.time}")
            AppBodyPrimary(text = "Date: ${uiState.date}")
            AppBodyPrimary(text = "Presenter today: ${uiState.presenter}")

            AppSectionTitle(text = "Absent Information")
            AppOutlineTextField(
                label = "Reason",
                value = uiState.reasonToAbsent ?: "",
                onValueChange = changeReasonToAbsent,
                modifier = Modifier.fillMaxWidth(),
                readOnly = !uiState.isWantToAbsent
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = uiState.isWantToAbsent,
                    onClick = changeWantToAbsent
                )
                AppBodyPrimary(
                    text = "I want to be absent from this class",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        AppButton(
            onClick = onSubmit,
            enabled = uiState.isWantToAbsent,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppLabelPrimary("Submit now")
        }
    }
}

@Preview(showBackground = true, name = "Default State")
@Composable
private fun StudentLessonInfoScreenPreview() {
    AttendanceAssistantTheme {
        StudentLessonInfoScreen(
            uiState = StudentLessonInfoUiState(
                courseId = "MTH253",
                courseName = "Calculus III",
                time = "10:00 AM",
                date = "Oct 26th",
                presenter = "Prof. Einstein",
                reasonToAbsent = "Doctor's appointment",
                isWantToAbsent = true
            ),
            changeReasonToAbsent = {},
            changeWantToAbsent = {},
            onSubmit = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
private fun StudentLessonInfoScreenLoadingPreview() {
    AttendanceAssistantTheme {
        StudentLessonInfoScreen(
            uiState = StudentLessonInfoUiState(isLoading = true),
            changeReasonToAbsent = {},
            changeWantToAbsent = {},
            onSubmit = {}
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
private fun StudentLessonInfoScreenErrorPreview() {
    AttendanceAssistantTheme {
        StudentLessonInfoScreen(
            uiState = StudentLessonInfoUiState(errorMessage = "Failed to load details"),
            changeReasonToAbsent = {},
            changeWantToAbsent = {},
            onSubmit = {}
        )
    }
}

@Preview(showBackground = true, name = "Initial Empty State")
@Composable
private fun StudentLessonInfoScreenInitialEmptyPreview() {
    AttendanceAssistantTheme {
        StudentLessonInfoScreen(
            uiState = StudentLessonInfoUiState(
                courseId = "PHY101",
                courseName = "Physics I",
                time = "1:00 PM",
                date = "Nov 1st",
                presenter = "Dr. Feynman",
                reasonToAbsent = null,
                isWantToAbsent = false
            ),
            changeReasonToAbsent = {},
            changeWantToAbsent = {},
            onSubmit = {}
        )
    }
}
