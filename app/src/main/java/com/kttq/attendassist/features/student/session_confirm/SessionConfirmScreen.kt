package com.kttq.attendassist.features.student.session_confirm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.StudentSession
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun SessionConfirmRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: SessionConfirmViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    SessionConfirmScreen(
        uiState = uiState,
        onConfirmAttendance = {
            if (viewModel.confirmAttendance()) {
                // TODO: Navigate to a success screen or back to home with a message.
            }
        },
        modifier = modifier
    )
    LaunchedEffect(uiState.error) {
        onShowSnackbar(uiState.error ?: return@LaunchedEffect, null)
        viewModel.clearError()
    }
}

@Composable
fun SessionConfirmScreen(
    uiState: SessionConfirmUiState,
    onConfirmAttendance: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Course Details")
        AppLabelPrimary("Course ID: ${uiState.studentSession?.courseId ?: "N/A"}")
        AppLabelPrimary("Course Name: ${uiState.studentSession?.courseName ?: "N/A"}")
        AppLabelPrimary("Teacher Name: ${uiState.studentSession?.teacherName ?: "N/A"}")

        AppSectionTitle("Session Details")
        AppLabelPrimary("Session ID: ${uiState.studentSession?.sessionId ?: "N/A"}")
        AppLabelPrimary("Class ID: ${uiState.studentSession?.classId ?: "N/A"}")

        AppSectionTitle("Time Details")
        AppLabelPrimary("Start Time: ${uiState.studentSession?.startTime ?: "N/A"}")
        AppLabelPrimary("End Time: ${uiState.studentSession?.endTime ?: "N/A"}")

        if (uiState.attendanceConfirmed) {
            AppSectionTitle("Attendance Confirmation")
            AppLabelPrimary("Attendance has been confirmed for this session.")
        } else {
            AppButton(
                onClick = onConfirmAttendance,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth()
            ) {
                AppLabelPrimary("Confirm Attendance")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SessionConfirmScreenPreview() {
    AttendanceAssistantTheme {
        SessionConfirmScreen(
            uiState = SessionConfirmUiState(
                studentSession = StudentSession(
                    sessionId = 0,
                    classId = 0,
                    startTime = "",
                    endTime = "",
                    teacherId = "",
                    teacherName = "",
                    courseId = "",
                    courseName = ""
                ),
            ),
            onConfirmAttendance = {},
        )
    }

}