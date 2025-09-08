package com.kttq.attendassist.features.student.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: Replace with a more specific data class if needed for items in LazyColumn
data class DisplayItem(
    val id: String,
    val title: String,
    val description: String
)

data class StudentSummaryUiState(
    val isLoading: Boolean = false,
    val statSummary: List<Pair<String, String>> = emptyList(),
    val isIncomingSelected: Boolean = true, // Default selection
    val isAbsentSelected: Boolean = false,
    val isAttendedSelected: Boolean = false,
    val displayItems: List<DisplayItem> = emptyList()
    // TODO: Add error message state if needed
)

@HiltViewModel
class StudentSummaryViewModel @Inject constructor(
    // TODO: Inject necessary repositories (e.g., AttendanceRepository, ClassRepository)
    // private val attendanceRepository: AttendanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentSummaryUiState(isLoading = true))
    val uiState: StateFlow<StudentSummaryUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            // TODO: Fetch actual summary statistics from a repository
            val summaryStats = listOf(
                "Total" to "0",
                "Total leave" to "0",
                "Leave accepted" to "0",
                "Leave unaccepted" to "0"
            )
            _uiState.update { it.copy(statSummary = summaryStats) }
            // Initially load items for the default selected filter ("Incoming")
            fetchFilteredDisplayItems()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun selectIncoming() {
        if (_uiState.value.isIncomingSelected) return // No change if already selected
        _uiState.update {
            it.copy(
                isIncomingSelected = true,
                isAbsentSelected = false,
                isAttendedSelected = false
            )
        }
        fetchFilteredDisplayItems()
    }

    fun selectAbsent() {
        if (_uiState.value.isAbsentSelected) return
        _uiState.update {
            it.copy(
                isIncomingSelected = false,
                isAbsentSelected = true,
                isAttendedSelected = false
            )
        }
        fetchFilteredDisplayItems()
    }

    fun selectAttended() {
        if (_uiState.value.isAttendedSelected) return
        _uiState.update {
            it.copy(
                isIncomingSelected = false,
                isAbsentSelected = false,
                isAttendedSelected = true
            )
        }
        fetchFilteredDisplayItems()
    }

    private fun fetchFilteredDisplayItems() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, displayItems = emptyList()) }
            // TODO: Implement logic to fetch data based on the selected filter
            //  (isIncomingSelected, isAbsentSelected, isAttendedSelected)
            //  from the repository.
            //  For example:
            //  val items = when {
            //      _uiState.value.isIncomingSelected -> attendanceRepository.getIncomingItems()
            //      _uiState.value.isAbsentSelected -> attendanceRepository.getAbsentItems()
            //      _uiState.value.isAttendedSelected -> attendanceRepository.getAttendedItems()
            //      else -> emptyList()
            //  }
            // For now, using placeholder data based on selection:
            val placeholderItems = when {
                _uiState.value.isIncomingSelected -> listOf(DisplayItem("inc1", "Incoming Class 1", "Details..."))
                _uiState.value.isAbsentSelected -> listOf(DisplayItem("abs1", "Absent Record 1", "Details..."))
                _uiState.value.isAttendedSelected -> listOf(DisplayItem("att1", "Attended Class 1", "Details..."))
                else -> emptyList()
            }
            _uiState.update { it.copy(displayItems = placeholderItems, isLoading = false) }
        }
    }

    // TODO: Add any other functions needed, e.g., to handle item clicks from the LazyColumn
}
