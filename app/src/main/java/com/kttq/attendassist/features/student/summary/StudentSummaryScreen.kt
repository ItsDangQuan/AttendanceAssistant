package com.kttq.attendassist.features.student.summary

// import android.widget.GridLayout // Not used directly, can be removed
// import androidx.compose.material3.Card // Not used directly, AppCard is used
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun StudentSummaryRoute(
    modifier: Modifier = Modifier
) {
    StudentSummaryScreen(modifier = modifier)
}

@Composable
fun StudentSummaryScreen(
    modifier: Modifier = Modifier,
    studentSummaryViewModel: StudentSummaryViewModel = hiltViewModel()
) {
    val uiState by studentSummaryViewModel.uiState.collectAsState()
    // val statSummary = listOf( // Replaced with uiState.statSummary
    //     "Total" to "15",
    //     "Total leave" to "0",
    //     "Leave accepted" to "15",
    //     "Leave unaccepted" to "15"
    // )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_large)),
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
            items(uiState.statSummary.size) { index ->
                AppCard {
                    AppLabelPrimary(uiState.statSummary[index].first)
                    AppLabelSecondary(uiState.statSummary[index].second)
                }
            }
        }
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)))
        Row(
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
                selected = uiState.isAbsentSelected,
                onSelectedChange = { studentSummaryViewModel.selectAbsent() },
                label = { AppLabelPrimary("Absent") }
            )
            AppFilterChip(
                selected = uiState.isAttendedSelected,
                onSelectedChange = { studentSummaryViewModel.selectAttended() },
                label = { AppLabelPrimary("Attended") }
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) // Added for spacing
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            // TODO: Implement the view model to fetch data - ViewModel fetches, UI displays
            if (uiState.isLoading) {
                item {
                    Text("Loading items...") // Show a loading indicator
                }
            } else if (uiState.displayItems.isEmpty()) {
                item {
                    Text("No items to display for the selected filter.")
                }
            } else {
                items(uiState.displayItems.size) { index ->
                    val item = uiState.displayItems[index]
                    // Replace with your actual item composable
                    AppCard(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
                            AppLabelPrimary(text = item.title)
                            AppLabelSecondary(text = item.description)
                        }
                    }
                }
            }
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
