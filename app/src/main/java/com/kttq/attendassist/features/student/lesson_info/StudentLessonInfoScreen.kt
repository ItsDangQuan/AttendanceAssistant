package com.kttq.attendassist.features.student.lesson_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
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
fun StudentLessonInfoScreen(
    modifier: Modifier = Modifier,
    studentLessonInfoViewModel: StudentLessonInfoViewModel = hiltViewModel()
) {
    val uiState = studentLessonInfoViewModel.uiState
    Column (
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium))
    ){
        Column(
            modifier = modifier.fillMaxWidth().weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {
            AppSectionTitle(text = "Course Information")
            AppBodyPrimary(text = "Course ID: ${"Math"}")
            AppBodyPrimary(text = "Course Name: ${"MTH253"}")

            AppSectionTitle(text = "Attendance Information")
            AppBodyPrimary(text = "Time: ${"9:00"}")
            AppBodyPrimary(text = "Date: ${"Aug 1st"}")
            AppBodyPrimary(text = "Presenter today: ${"Mr.John Doe"}")

            AppSectionTitle(text = "Absent Information")
            AppOutlineTextField(
                label = "Reason",
                value = uiState.reasonToAbsent ?: "",
                onValueChange = {
                    studentLessonInfoViewModel.changeReasonToAbsent(it)
                },
                modifier = Modifier.fillMaxWidth(),
                readOnly = !uiState.isWantToAbsent
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = uiState.isWantToAbsent,
                    onClick = {
                        studentLessonInfoViewModel.changeWantToAbsent()
                    }
                )
                AppBodyPrimary(text = "I want to be absent from this class", modifier = Modifier.weight(1f))
            }
        }

        AppButton(
            onClick = {
                //TODO: Handle student click button
            },
            enabled = uiState.isWantToAbsent,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppLabelPrimary("Submit now")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentLessonInfoScreenPreview() {
    AttendanceAssistantTheme {
        StudentLessonInfoScreen()
    }
}