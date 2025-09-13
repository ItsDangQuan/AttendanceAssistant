package com.kttq.attendassist.features.teacher.class_past_session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
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
    val currentStudent = viewModel.currentsStudentsList.collectAsStateWithLifecycle().value?: emptyList()
    val absentStudent = totalStudent - currentStudent
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
    currentStudent: List<StudentProfile>,
    absentStudent: List<StudentProfile>,
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
        LazyColumn(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(currentStudent) { student ->
                AppCard (modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)))
                {
                    AppBodySecondary(text = student.studentId)
                    AppBodySecondary(text = student.firstName + " " + student.lastName)
                }
            }
        }
        AppSectionTitle("Absent student")
        LazyColumn(
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