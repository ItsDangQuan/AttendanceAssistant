package com.kttq.attendassist.features.teacher.class_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.data.network.models.Class
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.components.AppTextButton

@Composable
fun ClassDetailRoute(
    navigateToSessionDetail: (Class) -> Unit,
    navigateToStudentDetail: (Class) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassDetailViewModel = hiltViewModel(),
) {
    // TODO: Handle the case
    ClassDetailScreen(
        classDetail = viewModel.uiState.collectAsStateWithLifecycle().value.classDetails!!,
        isAdvertising = viewModel.uiState.collectAsStateWithLifecycle().value.isAdvertising,
        navigateToSessionDetail = navigateToSessionDetail,
        navigateToStudentDetail = navigateToStudentDetail,
        modifier = modifier
    )
}

@Composable
fun ClassDetailScreen(
    classDetail: Class,
    isAdvertising: Boolean,
    navigateToSessionDetail: (Class) -> Unit,
    navigateToStudentDetail: (Class) -> Unit,
    startScan: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Overview")
        AppBodyPrimary("Class ID: ${classDetail.classId}")
        AppBodyPrimary("Course ID: ${classDetail.classId}")
        AppBodyPrimary("Course Name: ${classDetail.courseName}")
        AppTextButton(
            onClick = { navigateToSessionDetail(classDetail) },
            content = {
                AppLabelPrimary(
                    text = "Show all past attendance sessions"
                )
            }
        )
        AppTextButton(
            onClick = { navigateToStudentDetail(classDetail) },
            content = {
                AppLabelPrimary(
                    text = "Show all students"
                )
            }
        )

        AppButton(
            onClick = startScan,
            enabled = !isAdvertising
        ) {
            AppLabelPrimary(text = "Start new attendance session")
        }
    }
}