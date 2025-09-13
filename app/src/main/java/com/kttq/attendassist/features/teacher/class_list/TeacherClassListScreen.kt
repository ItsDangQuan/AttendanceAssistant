package com.kttq.attendassist.features.teacher.class_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun TeacherClassListRoute(
    navigateToClass: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TeacherClassListViewModel = hiltViewModel()
) {
    TeacherClassListScreen(
        viewModel.currentClasses.collectAsState().value,
        navigateToClass,
        modifier = modifier
    )
}

@Composable
fun TeacherClassListScreen(
    currentClasses: List<Class>,
    navigateToClass: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        AppScreenTitle("Current Classes")
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
                    AppBodyPrimary(currentClasses[it].className ?: "")
                    AppBodySecondary(
                        currentClasses[it].courseId + " - " +
                                currentClasses[it].courseName
                    )
                }
            }
        }
        // HorizontalDivider(
        //     modifier = Modifier.fillMaxWidth()
        //         .padding(dimensionResource(R.dimen.padding_large)),
        //     thickness = dimensionResource(R.dimen.divider_height),
        // )
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
fun TeacherClassListScreenPreview(modifier: Modifier = Modifier) {
    AttendanceAssistantTheme {
        TeacherClassListScreen(
            currentClasses = emptyList(),
            navigateToClass = {}
        )

    }
}