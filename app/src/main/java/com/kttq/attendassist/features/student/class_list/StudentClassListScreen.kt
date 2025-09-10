package com.kttq.attendassist.features.student.class_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.core.data.network.models.Class
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppScreenTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun StudentClassListRoute(
    modifier: Modifier = Modifier,
    navigateToClass: (Class) -> Unit = {},
    viewModel: StudentClassListViewModel = hiltViewModel()
) {
   StudentClassListScreen(
       viewModel.currentClasses.collectAsState().value,
       viewModel.pastClasses.collectAsState().value,
       navigateToClass,
       modifier = modifier
   )
}

@Composable
fun StudentClassListScreen(

    currentClasses: List<Class>,
    pastClasses: List<Class>,
    navigateToClass: (Class) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        AppScreenTitle("Current Course")
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(
                currentClasses.size
            ) {
                AppCard (
                    clickable = true,
                    onClick = { navigateToClass(currentClasses[it]) }
                ) {
                    AppLabelPrimary(currentClasses[it].classId)
                    AppLabelSecondary(
                        currentClasses[it].courseId + " - " +
                                currentClasses[it].courseName
                    )
                }
            }
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_large)),
            thickness = dimensionResource(R.dimen.divider_height),
            )
        AppScreenTitle("Pass Course")
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            items(
                pastClasses.size
            ) {
                AppCard (
                    clickable = true,
                    onClick = { navigateToClass(pastClasses[it]) }
                ) {
                    AppLabelPrimary(pastClasses[it].classId)
                    AppLabelSecondary(
                        pastClasses[it].courseId + " - " +
                                pastClasses[it].courseName
                    )
                }
            }

        }
    }

}

@Preview (showBackground = true)
@Composable
private fun StudentClassListScreenPreview() {
    AttendanceAssistantTheme {
        StudentClassListScreen(
            currentClasses = emptyList(),
            pastClasses = emptyList(),
            navigateToClass = {}
        )
    }
}