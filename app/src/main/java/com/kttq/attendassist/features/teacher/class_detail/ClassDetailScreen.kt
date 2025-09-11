package com.kttq.attendassist.features.teacher.class_detail

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
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.components.AppTextButton
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun ClassDetailRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    navigateToSessionDetail: (String) -> Unit,
    navigateToStudentDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassDetailViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    // TODO: Handle the case
    ClassDetailScreen(
        classDetail = uiState.classDetails!!,
        isAdvertising = uiState.isAdvertising,
        navigateToSessionDetail = navigateToSessionDetail,
        navigateToStudentDetail = navigateToStudentDetail,
        startAdvertise = viewModel::startNewAttendanceSession,
        modifier = modifier
    )
    LaunchedEffect(uiState.error) {
        onShowSnackbar(uiState.error?:"Unknown error", null)
    }
}

@Composable
fun ClassDetailScreen(
    classDetail: Class,
    isAdvertising: Boolean,
    navigateToSessionDetail: (String) -> Unit,
    navigateToStudentDetail: (String) -> Unit,
    startAdvertise: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Overview")
        AppBodyPrimary("Class ID: ${classDetail.classId}")
        AppBodyPrimary("Course ID: ${classDetail.classId}")
        AppBodyPrimary("Course Name: ${classDetail.courseName}")
        AppTextButton(
            onClick = { navigateToSessionDetail(classDetail.classId) },
            content = {
                AppLabelPrimary(
                    text = "Show all past attendance sessions"
                )
            }
        )
        AppTextButton(
            onClick = { navigateToStudentDetail(classDetail.classId) },
            content = {
                AppLabelPrimary(
                    text = "Show all students"
                )
            }
        )

        AppButton(
            onClick = startAdvertise,
            enabled = !isAdvertising
        ) {
            AppLabelPrimary(text = "Start new attendance session")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ClassDetailScreenPreview() {
    AttendanceAssistantTheme {
        ClassDetailScreen(
            classDetail = Class(
                classId = "123",
                semester = "Autumn",
                year = 2025,
                courseId = "CS161",
                courseName = "Mobile App Development",
                teacherId = "123",
                teacherName = "John Doe",
            ),
            isAdvertising = false,
            navigateToSessionDetail = {},
            navigateToStudentDetail = {},
            startAdvertise = {}
        )
    }
}