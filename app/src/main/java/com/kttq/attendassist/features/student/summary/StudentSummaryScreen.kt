package com.kttq.attendassist.features.student.summary

import android.widget.GridLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppFilterChip
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun StudentSummaryScreen(
    modifier: Modifier = Modifier,
    studentSummaryViewModel: StudentSummaryViewModel = hiltViewModel()
) {
    val uiState = studentSummaryViewModel.uiState.value
    //TODO: Replace that with real value
    val statSummary = listOf(
        "Total" to "15",
        "Total leave" to "0",
        "Leave accepted" to "15",
        "Leave unaccepted" to "15"
    )
    Column (
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppSectionTitle(text = "Student Summary")
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
           items(statSummary.size) { index ->
               AppCard {
                   AppLabelPrimary(statSummary[index].first)
                   AppLabelSecondary(statSummary[index].second)
               }
           }
        }
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {
            AppFilterChip(
                selected = uiState.isIncomingSelected,
                onSelectedChange = { studentSummaryViewModel.selectIncoming() },
                label = { AppLabelPrimary("Incoming") }
            )
            AppFilterChip(
                selected =  uiState.isAbsentSelected,
                onSelectedChange = { studentSummaryViewModel.selectAbsent() },
                label = { AppLabelPrimary("Absent") }
            )
            AppFilterChip(
                selected = uiState.isAttendedSelected,
                onSelectedChange = { studentSummaryViewModel.selectAttended() },
                label = { AppLabelPrimary("Attended") }
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            // TODO: Implement the view model to fetch data
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StudentSummaryScreenPreview() {
    AttendanceAssistantTheme {
        StudentSummaryScreen()
    }
}