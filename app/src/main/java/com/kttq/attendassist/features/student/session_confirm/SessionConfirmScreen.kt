package com.kttq.attendassist.features.student.session_confirm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.data.network.responses.Session
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun SessionConfirmRoute (
    modifier: Modifier = Modifier,
    viewModel: SessionConfirmViewModel = hiltViewModel()
) {
    SessionConfirmScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onConfirmAttendance = viewModel::confirmAttendance,
        modifier = modifier
    )
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
       AppLabelPrimary("Course ID: ${uiState.session?.courseId ?: "N/A"}")
       AppLabelPrimary("Course Name: ${uiState.session?.courseName ?: "N/A"}")
       AppLabelPrimary("Teacher Name: ${uiState.session?.teacherName ?: "N/A"}")

       AppSectionTitle("Session Details")
       AppLabelPrimary("Session ID: ${uiState.session?.sessionId ?: "N/A"}")
       AppLabelPrimary("Class ID: ${uiState.session?.classId ?: "N/A"}")

       AppSectionTitle("Time Details")
       AppLabelPrimary("Start Time: ${uiState.session?.startTime ?: "N/A"}")
       AppLabelPrimary("End Time: ${uiState.session?.endTime ?: "N/A"}")

       if (uiState.attendanceConfirmed) {
           AppSectionTitle("Attendance Confirmation")
           AppLabelPrimary("Attendance has been confirmed for this session.")
       }
       else {
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

@Preview
@Composable
private fun SessionConfirmScreenPreview() {
    AttendanceAssistantTheme {
        SessionConfirmScreen(
            uiState = SessionConfirmUiState(
                session = Session(
                    sessionId = "",
                    classId = "",
                    startTime = 0,
                    endTime = "",
                    teacherId = "",
                    className = "",
                    teacherName = "",
                    courseId = "",
                    courseName = ""
                ),
            ),
            onConfirmAttendance = {},
        )
    }
    
}