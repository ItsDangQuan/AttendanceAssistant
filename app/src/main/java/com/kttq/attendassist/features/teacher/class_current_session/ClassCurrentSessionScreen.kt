package com.kttq.attendassist.features.teacher.class_current_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun ClassCurrentSessionRoute(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ClassCurrentSessionViewModel = hiltViewModel()
) {

    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val totalStudent = viewModel.studentsList.collectAsStateWithLifecycle().value
    val currentStudent = viewModel.currentsStudentsList.collectAsStateWithLifecycle().value
    val absentStudent = currentStudent?.let { totalStudent?.let { totalStudent - currentStudent } }


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
    currentStudent: List<StudentProfile>?,
    absentStudent: List<StudentProfile>?,
    onStopAdvertise: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AppSectionTitle("Student in class")
            if (currentStudent == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else {
                LazyColumn(
                    contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    modifier = Modifier.weight(1f)
                ) {
                    items(currentStudent) { student ->
                        AppCard {
                            AppBodyPrimary(text = student.studentId)
                            AppBodyPrimary(text = student.firstName + " " + student.lastName)
                        }
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
            AppSectionTitle("Absent student")
            if (absentStudent == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else {
                LazyColumn(
                    contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
                    modifier = Modifier.weight(1f)
                ) {
                    items(absentStudent) { student ->
                        AppCard {
                            AppBodyPrimary(text = student.studentId)
                            AppBodyPrimary(text = student.firstName + " " + student.lastName)
                        }
                    }
                }
            }
        }
        AppButton(onClick = onStopAdvertise, modifier = modifier.fillMaxWidth()) {
            AppLabelPrimary("Stop Attendance")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CurrentSessionScreenPreview() {
    AttendanceAssistantTheme {
        ClassCurrentSessionScreen(
            listOf(
                StudentProfile(
                    studentId = "InClassStudentIdStart",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "InClassStudentIdEnd",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
            ),
            listOf(
                StudentProfile(
                    studentId = "AbsentStudentIdStart",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentId",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
                StudentProfile(
                    studentId = "AbsentStudentIdEnd",
                    email = "Email",
                    firstName = "FirstName",
                    lastName = "LastName",
                ),
            ),
            {}
        )
    }
}