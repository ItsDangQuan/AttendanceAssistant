package com.kttq.attendassist.features.student.class_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.ui.components.AppBodyPrimary
import com.kttq.attendassist.core.ui.components.AppBodySecondary
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun StudentClassListRoute(
    navigateToClass: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StudentClassListViewModel = hiltViewModel()
) {
    StudentClassListScreen(
        viewModel.currentClasses.collectAsState().value,
        navigateToClass,
        modifier = modifier
    )
}

@Composable
fun StudentClassListScreen(
    currentClasses: List<Class>,
    navigateToClass: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        AppScreenTitle("Current Course")
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(
                currentClasses.size
            ) {
                AppCard(
                    clickable = true,
                    onClick = { navigateToClass(currentClasses[it].classId) }
                ) {
                    AppBodyPrimary(
                        currentClasses[it].courseId + " - " +
                                currentClasses[it].courseName
                    )
                    AppBodyPrimary(currentClasses[it].className ?: "")
                    AppBodySecondary(currentClasses[it].teacherName)
                }
            }
        }
        // HorizontalDivider(
        //     modifier = Modifier.fillMaxWidth()
        //         .padding(dimensionResource(R.dimen.padding_large)),
        //     thickness = dimensionResource(R.dimen.divider_height),
        //     )
        // AppScreenTitle("Pass Course")
        // LazyColumn(
        //     modifier = Modifier.fillMaxWidth(),
        //     horizontalAlignment = Alignment.CenterHorizontally,
        //     verticalArrangement = Arrangement.Top
        // ) {
        //     items(
        //         pastClasses.size
        //     ) {
        //         AppCard (
        //             clickable = true,
        //             onClick = { navigateToClass(pastClasses[it].classId) }
        //         ) {
        //             AppLabelPrimary(pastClasses[it].classId)
        //             AppLabelSecondary(
        //                 pastClasses[it].courseId + " - " +
        //                         pastClasses[it].courseName
        //             )
        //             AppLabelSecondary(pastClasses[it].teacherName)
        //         }
        //     }

        // }
    }

}

@Preview(showBackground = true)
@Composable
private fun StudentClassListScreenPreview() {
    AttendanceAssistantTheme {
        StudentClassListScreen(
            currentClasses = emptyList(),
            navigateToClass = {}
        )
    }
}