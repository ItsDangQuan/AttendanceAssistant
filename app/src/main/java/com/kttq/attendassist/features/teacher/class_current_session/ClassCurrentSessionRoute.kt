package com.kttq.attendassist.features.teacher.class_current_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle


@Composable
fun ClassCurrentSessionRoute(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassCurrentSessionViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val totalStudent = viewModel.studentsList.collectAsStateWithLifecycle().value?: emptyList()
    val currentStudent = viewModel.currentsStudentsList.collectAsStateWithLifecycle().value?: emptyList()
    val absentStudent = totalStudent - currentStudent


    LaunchedEffect(uiState.isStop) {
        if (uiState.isStop) {
            onNavigateBack()
        }
    }

    ClassCurrentSessionScreen(
        currentStudent = currentStudent,
        absentStudent = absentStudent,
        onStopAdvertise = {
            viewModel.stopCurrentAttendanceSession()
        },
        modifier = modifier
    )
}

@Composable
fun ClassCurrentSessionScreen(
    currentStudent: List<StudentProfile>,
    absentStudent: List<StudentProfile>,
    onStopAdvertise: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Student in class")
        LazyColumn(
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            items(currentStudent) { student ->
                AppCard {
                    AppLabelPrimary(text = student.studentId)
                    AppLabelPrimary(text = student.firstName + " " + student.lastName)
                }
            }
        }
        HorizontalDivider(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        AppSectionTitle("Absent student")
        LazyColumn (
            contentPadding = PaddingValues(dimensionResource(R.dimen.padding_medium)),

            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ){
            items(absentStudent) { student ->
                AppCard {
                    AppLabelPrimary(text = student.studentId)
                    AppLabelPrimary(text = student.firstName + " " + student.lastName)
                }
            }
        }
        AppButton(onClick = onStopAdvertise) {
            AppLabelPrimary("Stop Attendance")
        }
    }
}

@Preview
@Composable
private fun CurrentSessionScreenPreview() {
}