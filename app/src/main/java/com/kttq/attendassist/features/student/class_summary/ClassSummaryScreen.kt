package com.kttq.attendassist.features.student.class_summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kttq.attendassist.R
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.model.Record
import com.kttq.attendassist.core.ui.components.AppBodyCaption
import com.kttq.attendassist.core.ui.components.AppCard
import com.kttq.attendassist.core.ui.components.AppIconButton
import com.kttq.attendassist.core.ui.components.AppLabelPrimary
import com.kttq.attendassist.core.ui.components.AppLabelSecondary
import com.kttq.attendassist.core.ui.components.AppSectionTitle
import com.kttq.attendassist.core.ui.theme.AttendanceAssistantTheme


@Composable
fun ClassSummaryRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: ClassSummaryViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val statSummary = viewModel.statSummary.collectAsStateWithLifecycle().value

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
            )
        }
    } else if (uiState.error != null) {
        LaunchedEffect(
            uiState.error,
        ) {
            onShowSnackbar(uiState.error, null)
        }
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
    ) {
        AppSectionTitle("Class Information")
        AppBodyCaption("Course ID: ${classDetail.courseId}")
        AppBodyCaption("Course Name: ${classDetail.courseName}")
        AppBodyCaption("Class Name: ${classDetail.className}")
        AppBodyCaption("Semester: ${classDetail.semester}")
        AppBodyCaption("Year: ${classDetail.year}")

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        AppSectionTitle("Summary")
        Row (
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small)), // Added vertical padding
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            statSummary.forEach { pair ->
                AppCard(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)).weight(1f)
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
                    AppCard(
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                AppLabelPrimary("Session: ${record.sessionId}") // Changed to show session ID
                                AppLabelSecondary("Status: ${record.status}")
                            }
                            // // Consider making status comparison case-insensitive and using constants
                            // if (record.status.equals("leaveUnaccepted", ignoreCase = true)) {
                            //     AppIconButton(
                            //         iconId = R.drawable.ic_edit, // Ensure this drawable exists
                            //         onClick = { /*TODO: Create another bottom sheet to handle or navigate*/ }
                            //     )
                            // }
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
        classId = 0,
        className = "23TT2",
        courseId = "CSE303",
        semester = "Spring",
        year = 2024,
        teacherId = "TCH001",
        teacherName = "John Doe",
        courseName = "Mobile Application Development",
    )

    val sampleRecords = listOf(
        Record(
            recordId = "REC001",
            studentId = "STU001",
            status = "Attended",
            sessionId = 0,
        ),
        Record(
            recordId = "REC003",
            studentId = "STU001",
            status = "LeaveAccepted",
            sessionId = 1,
        ),
        Record(
            recordId = "REC004",
            studentId = "STU001",
            status = "LeaveUnaccepted",
            sessionId = 2
        ),
        Record(
            recordId = "REC005",
            studentId = "STU001",
            status = "Attended",
            sessionId = 3
        )
    )

    val sampleStatSummary =  listOf(
        "Total Sessions" to "0",
        "Attended Sessions" to "0",
        "Absent Sessions" to "0",
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
        classId = 0,
        className = "23TT2",
        courseId = "MAT201",
        semester = "Fall",
        year = 2023,
        teacherId = "TCH002",
        teacherName = "Jane Smith",
        courseName = "Calculus I",
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
