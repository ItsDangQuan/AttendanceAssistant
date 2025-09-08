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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppFilterChip
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme

@Composable
fun StudentSummaryRoute(
    modifier: Modifier = Modifier,
    viewModel: StudentSummaryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StudentSummaryScreen(
        statSummary = uiState.statSummary,
        isIncomingSelected = uiState.isIncomingSelected,
        isAbsentSelected = uiState.isAbsentSelected,
        isAttendedSelected = uiState.isAttendedSelected,
        isLoading = uiState.isLoading,
        displayItems = uiState.displayItems,
        selectIncoming = viewModel::selectIncoming,
        selectAbsent = viewModel::selectAbsent,
        selectAttended = viewModel::selectAttended,
        modifier = modifier
    )
}

@Composable
fun StudentSummaryScreen(
    statSummary: List<Pair<String, String>>,
    isIncomingSelected: Boolean,
    isAbsentSelected: Boolean,
    isAttendedSelected: Boolean,
    isLoading: Boolean,
    displayItems: List<DisplayItem>,
    selectIncoming: () -> Unit,
    selectAbsent: () -> Unit,
    selectAttended: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            items(statSummary.size) { index ->
                AppCard {
                    AppLabelPrimary(statSummary[index].first)
                    AppLabelSecondary(statSummary[index].second)
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
                selected = isIncomingSelected,
                onSelectedChange = { selectIncoming() },
                label = { AppLabelPrimary("Incoming") }
            )
            AppFilterChip(
                selected = isAbsentSelected,
                onSelectedChange = { selectAbsent() },
                label = { AppLabelPrimary("Absent") }
            )
            AppFilterChip(
                selected = isAttendedSelected,
                onSelectedChange = { selectAttended() },
                label = { AppLabelPrimary("Attended") }
            )
        }
        Spacer(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) // Added for spacing
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            // TODO: Implement the view model to fetch data - ViewModel fetches, UI displays
            if (isLoading) {
                item {
                    Text("Loading items...") // Show a loading indicator
                }
            } else if (displayItems.isEmpty()) {
                item {
                    Text("No items to display for the selected filter.")
                }
            } else {
                items(displayItems.size) { index ->
                    val item = displayItems[index]
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
        StudentSummaryScreen(
            statSummary = emptyList(),
            isIncomingSelected = false,
            isAbsentSelected = false,
            isAttendedSelected = false,
            isLoading = false,
            displayItems = emptyList(),
            selectIncoming = {},
            selectAbsent = {},
            selectAttended = {}
        )
    }
}
