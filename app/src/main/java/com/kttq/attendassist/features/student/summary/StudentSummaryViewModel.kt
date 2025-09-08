package com.kttq.attendassist.features.student.summary

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentSummaryViewModel @Inject constructor() : ViewModel() {

    // Private mutable state
    private val _uiState = mutableStateOf(StudentSummaryUiState())
    // Public immutable state
    val uiState: State<StudentSummaryUiState> = _uiState



    fun selectIncoming() {
        _uiState.value = _uiState.value.copy(
            isIncomingSelected = true,
            isAbsentSelected = false,
            isAttendedSelected = false
        )
    }

    fun selectAbsent() {
        _uiState.value = _uiState.value.copy(
            isIncomingSelected = false,
            isAbsentSelected = true,
            isAttendedSelected = false
        )
    }

    fun selectAttended() {
        _uiState.value = _uiState.value.copy(
            isIncomingSelected = false,
            isAbsentSelected = false,
            isAttendedSelected = true
        )
    }
}

data class StudentSummaryUiState(
    val isIncomingSelected: Boolean = true,
    val isAbsentSelected: Boolean = false,
    val isAttendedSelected: Boolean = false
)
