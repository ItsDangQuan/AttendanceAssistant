package com.kttq.attendassist.features.teacher.lesson_info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppButton
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun TeacherLessonInfoRoute(
    modifier: Modifier = Modifier,
    viewModel: TeacherLessonInfoViewModel = hiltViewModel()
) {
    TeacherLessonInfoScreen(modifier = modifier)
}

@Composable
fun TeacherLessonInfoScreen(
    modifier: Modifier = Modifier,
) {
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
            AppBodyPrimary(text = "Course ID: ${"Math"}")
            AppBodyPrimary(text = "Course Name: ${"MTH253"}")

            AppSectionTitle(text = "Attendance Information")
            AppBodyPrimary(text = "Time: ${"9:00"}")
            AppBodyPrimary(text = "Date: ${"Aug 1st"}")
            AppBodyPrimary(text = "Presenter today: ${"Mr.John Doe"}")

            AppSectionTitle(text = "Statistics")

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                AppCard(
                    modifier = Modifier.weight(1f)
                ) {
                    AppLabelPrimary("Total:")
                    AppLabelSecondary("N/A")
                }

                AppCard(
                    modifier = Modifier.weight(1f)
                ) {
                    AppLabelPrimary("Already checked:")
                    AppLabelSecondary("N/A")
                }
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
        ) {

            AppButton(
                onClick = {
                    //TODO: Handle student click button
                },
                modifier = Modifier.weight(1f)
            ) {
                AppLabelPrimary("Stop checking")
            }

            AppButton(
                onClick = {
                    //TODO: Handle student click button
                },
                modifier = Modifier.weight(1f)
            ) {
                AppLabelPrimary("Export")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TeacherLessonInfoScreenPreview() {
    AttendanceAssistantTheme {
        TeacherLessonInfoScreen()
    }
}