package com.kttq.attendassist.features.student.summary

// import android.widget.GridLayout // Not used directly, can be removed
// import androidx.compose.material3.Card // Not used directly, AppCard is used
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Log.d("StudentSummaryRoute", "Recomposing StudentSummaryRoute")
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
    // Debug log to see sequences while navigating
    Log.d("StudentSummary", "isLoading=$isLoading, items=${displayItems.size}")

    Column(
        modifier = modifier
            .fillMaxSize() // occupy full screen so inner changes don't push bottom bar
            .padding(dimensionResource(R.dimen.padding_large)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppSectionTitle(text = "Student Summary")
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        // Reserve a small stable height so the grid doesn't measure to zero
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 88.dp) // reserve a bit of vertical space
                .padding(vertical = dimensionResource(R.dimen.padding_small)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(
                items = statSummary,
                key = { it.first } // use label as stable key
            ) { pair ->
                AppCard {
                    AppLabelPrimary(pair.first)
                    AppLabelSecondary(pair.second)
                }
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround // avoid Absolute.SpaceAround
        ) {
            AppFilterChip(selected = isIncomingSelected, onSelectedChange = { selectIncoming() }) {
                AppLabelPrimary("Incoming")
            }
            AppFilterChip(selected = isAbsentSelected, onSelectedChange = { selectAbsent() }) {
                AppLabelPrimary("Absent")
            }
            AppFilterChip(selected = isAttendedSelected, onSelectedChange = { selectAttended() }) {
                AppLabelPrimary("Attended")
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

        // MAIN SCROLL AREA: weight(1f) prevents it from resizing the parent when content changes
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .animateContentSize() // optional: smooth small height changes
        ) {
            if (isLoading) {
                // show a centered placeholder of stable size
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Loading items...")
                    // consider skeletons with fixed heights here
                }
            } else if (displayItems.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No items to display for the selected filter.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    // IMPORTANT: use stable key. Replace `it.id` with your stable id property.
                    items(
                        items = displayItems,
                        key = { item ->
                            // prefer a stable unique id: item.id
                            // Fallback if you don't have an id: use a stable property instead (title) or hashCode()
                            item.id // <- replace with your id field; if none, use item.title or item.hashCode()
                        }
                    ) { item ->
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
