package com.kttq.attendassist.features.student.class_summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.data.network.models.Class
import com.kttq.attendassist.core.data.network.models.Record
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
// import com.kttq.attendassist.core.ui.components.AppScreenTitle // Not used in this screen directly
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun ClassSummaryRoute(
    modifier: Modifier = Modifier,
    viewModel: ClassSummaryViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val statSummary = viewModel.statSummary.collectAsStateWithLifecycle().value

    if (uiState.isLoading) {
        // TODO: Show a loading indicator, e.g., CircularProgressIndicator
        AppLabelPrimary(text = "Loading class summary...") 
    } else if (uiState.error != null) {
        // TODO: Show a more user-friendly error message, possibly with a retry option
        AppLabelPrimary(text = "Error: ${uiState.error}")
    } else if (uiState.classDetails != null) {
        ClassSummaryScreen(
            classDetail = uiState.classDetails,
            records = uiState.records,
            statSummary = statSummary,
            modifier = modifier
        )
    } else {
        // Fallback for unexpected state, though ideally covered by isLoading/error
        AppLabelPrimary(text = "No class details available.")
    }
}

@Composable
fun ClassSummaryScreen(
    classDetail: Class,
    records: List<Record>,
    statSummary: List<Pair<String, String>>,
    modifier: Modifier = Modifier

) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Class Information")
        AppLabelPrimary("Course ID: ${classDetail.courseId}")
        AppLabelPrimary("Course Name: ${classDetail.courseName}")
        AppLabelPrimary("Class ID: ${classDetail.classId}")
        AppLabelPrimary("Semester: ${classDetail.semester}")
        AppLabelPrimary("Year: ${classDetail.year}")

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        AppSectionTitle("Summary")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimensionResource(R.dimen.padding_small)), // Added vertical padding
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
        ) {
            items(
                items = statSummary,
                key = { it.first } // use label as stable key
            ) { pair ->
                AppCard(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                ) {
                    AppLabelPrimary(pair.first)
                    AppLabelSecondary(pair.second)
                }
            }
        }
        AppSectionTitle("Records")
        if (records.isEmpty()) {
            AppLabelSecondary(text = "No records found for this class.")
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)) // Added spacing
            ) {
                items(
                    items = records, // Changed to use records directly for better stability with keys if available
                    key = { record -> record.recordId } // Assuming recordId is a stable unique key
                ) { record ->
                    AppCard {
                        Row (
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column (
                                modifier = Modifier.weight(1f)
                            ) {
                                AppLabelPrimary("Session: ${record.session}") // Changed to show session ID
                                AppLabelSecondary("Status: ${record.status}")
                            }
                            // Consider making status comparison case-insensitive and using constants
                            if (record.status.equals("leaveUnaccepted", ignoreCase = true)) {
                                AppIconButton(
                                    iconId = R.drawable.ic_edit, // Ensure this drawable exists
                                    onClick = { /*TODO: Create another bottom sheet to handle or navigate*/ }
                                )
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
private fun ClassSummaryScreenPreview() {
    val sampleClassDetail = Class(
        classId = "CLS101",
        courseId = "CSE303",
        semester = "Spring",
        year = 2024,
        teacherId = "TCH001",
        courseName = "Mobile Application Development"
    )

    val sampleRecords = listOf(
        Record(recordId = "REC001", studentId = "STU001", status = "Attended", session = "SES001"),
        Record(recordId = "REC003", studentId = "STU001", status = "LeaveAccepted", session = "SES003"),
        Record(recordId = "REC004", studentId = "STU001", status = "LeaveUnaccepted", session = "SES004"),
        Record(recordId = "REC005", studentId = "STU001", status = "Attended", session = "SES005")
    )

    val sampleStatSummary = listOf(
        "Total Sessions" to "4",
        "Attended" to "2",
        "Leave Accepted" to "1",
        "Leave Unaccepted" to "1"
    )

    AttendanceAssistantTheme {
        ClassSummaryScreen(
            classDetail = sampleClassDetail,
            records = sampleRecords,
            statSummary = sampleStatSummary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ClassSummaryScreenEmptyRecordsPreview() {
    val sampleClassDetail = Class(
        classId = "CLS102",
        courseId = "MAT201",
        semester = "Fall",
        year = 2023,
        teacherId = "TCH002",
        courseName = "Calculus I"
    )

    val sampleStatSummaryEmpty = listOf(
        "Total Sessions" to "0",
        "Attended" to "0",
        "Leave Accepted" to "0",
        "Leave Unaccepted" to "0"
    )

    AttendanceAssistantTheme {
        ClassSummaryScreen(
            classDetail = sampleClassDetail,
            records = emptyList(),
            statSummary = sampleStatSummaryEmpty
        )
    }
}
