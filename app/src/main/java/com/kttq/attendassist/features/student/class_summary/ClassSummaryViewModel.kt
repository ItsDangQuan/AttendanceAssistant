package com.kttq.attendassist.features.student.class_summary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.data.network.models.Class
import com.kttq.attendassist.core.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.isNotBlank
import com.kttq.attendassist.core.data.network.models.Record
import dagger.hilt.android.lifecycle.HiltViewModel

data class ClassSummaryUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val classDetails: Class? = null,
    val records: List<Record> = emptyList()
)

@HiltViewModel
class ClassSummaryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val classId = savedStateHandle.toRoute<Destination.Student.ClassSummary>().classId

    private val _uiState = MutableStateFlow(ClassSummaryUiState())
    val uiState = _uiState.asStateFlow()



    init {
        fetchData(classId)
    }

    val statSummary: StateFlow<List<Pair<String, String>>> =
        _uiState.map { state ->
            val records = state.records
            val totalCount = records.size
            // TODO: Define status strings consistently (e.g., as enums or constants)
            val attendedCount = records.count { it.status.equals("attended", ignoreCase = true) }
            val absentCount = records.count { it.status.equals("absent", ignoreCase = true) } // Assuming "absent" status
            val leaveAcceptedCount = records.count { it.status.equals("leaveAccepted", ignoreCase = true) }
            val leaveUnacceptedCount = records.count { it.status.equals("leaveUnaccepted", ignoreCase = true) }

            listOf(
                "Total Sessions" to totalCount.toString(),
                "Attended" to attendedCount.toString(),
                "Absent" to absentCount.toString(),
                "Leave Accepted" to leaveAcceptedCount.toString(),
                "Leave Unaccepted" to leaveUnacceptedCount.toString()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf(
                "Total Sessions" to "0",
                "Attended" to "0",
                "Absent" to "0",
                "Leave Accepted" to "0",
                "Leave Unaccepted" to "0"
            )
        )

    init {
        if (classId != null && classId.isNotBlank()) {
            fetchData(classId)
        } else {
            _uiState.update { it.copy(isLoading = false, error = "Class ID not provided or invalid.") }
        }
    }

    private fun fetchData(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                // TODO: Fetch class details
                val details: Class? = null
                if (details == null) {
                    _uiState.update { it.copy(isLoading = false, error = "Class details not found.") }
                    return@launch
                }
                _uiState.update { it.copy(classDetails = details) }

                // Fetch records for the class by the student
                val studentId = null // TODO: Fetch student ID

                if (studentId == null) {
                    _uiState.update { it.copy(isLoading = false, error = "Student not identified. Cannot fetch records.") }
                    return@launch
                }
                val classRecords: List<Record>? = null
                // TODO: Handle the case where classRecords is null
                _uiState.update { it.copy(isLoading = false, records = classRecords!!) }

            } catch (e: Exception) {
                // TODO: Log the exception e
                _uiState.update { it.copy(isLoading = false, error = "Failed to load class summary: ${e.message}") }
            }
        }
    }}