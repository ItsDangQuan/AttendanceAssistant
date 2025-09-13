package com.kttq.attendassist.features.teacher.class_past_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.ui.components.AppBodyCaption
import com.kttq.attendassist.core.ui.components.AppBodySecondary
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun ClassPastSessionRoute(
    modifier: Modifier = Modifier,
    viewModel: ClassPastSessionViewModel = hiltViewModel()
) {
    val totalStudent = viewModel.studentsList.collectAsStateWithLifecycle().value
    val currentStudent = viewModel.currentsStudentsList.collectAsStateWithLifecycle().value
    val absentStudent = totalStudent?.let { total ->
        currentStudent?.let { current -> total - current }
    }

    val session = viewModel.session.collectAsStateWithLifecycle().value

    ClassPastSessionScreen(
        session = session,
        currentStudent = currentStudent,
        absentStudent = absentStudent,
        modifier = modifier
    )
}

@Composable
fun ClassPastSessionScreen(
    session: TeacherSession?,
    currentStudent: List<StudentProfile>?,
    absentStudent: List<StudentProfile>?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Session Information")
        AppBodySecondary("Session ID: ${session?.sessionId}")
        AppBodySecondary("Course ID: ${session?.courseId}")
        AppBodySecondary("Course Name: ${session?.courseName}")
        AppBodySecondary("Class Name: ${session?.className}")

        HorizontalDivider(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
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
                modifier = Modifier
                    .padding(bottom = dimensionResource(R.dimen.padding_medium))
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                items(currentStudent) { student ->
                    AppCard(modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)))
                    {
                        AppBodySecondary(text = student.studentId)
                        AppBodySecondary(text = student.firstName + " " + student.lastName)
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
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                items(absentStudent) { student ->
                    AppCard {
                        AppBodySecondary(text = student.studentId)
                        AppBodySecondary(text = student.firstName + " " + student.lastName)
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ClassPastSessionScreenPreview() {
    AttendanceAssistantTheme {
        ClassPastSessionScreen(
            session = TeacherSession(
                sessionId = 0,
                classId = 0,
                className = "ClassName",
                courseId = "CourseId",
                courseName = "CourseName",
                startTime = "2025-09-14 02:21",
                endTime = "2025-09-14 04:21"
            ),
            currentStudent = listOf(
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
            absentStudent = listOf(
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
            )
        )
    }
}