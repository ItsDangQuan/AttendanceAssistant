package com.kttq.attendassist.features.student.lesson_info

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentLessonInfoViewModel @Inject constructor(

) : ViewModel() {
    val _uiState = mutableStateOf(StudentLessonInfoUiState())
    val uiState = _uiState.value
    fun changeWantToAbsent() {
        _uiState.value = _uiState.value.copy(isWantToAbsent = !_uiState.value.isWantToAbsent)
        if (!_uiState.value.isWantToAbsent) {
            _uiState.value = _uiState.value.copy(reasonToAbsent = null)
        }
    }
    fun changeReasonToAbsent(reason: String) {
        _uiState.value = _uiState.value.copy(reasonToAbsent = reason)
    }
}

data class StudentLessonInfoUiState(
    val isWantToAbsent: Boolean = false,
    val reasonToAbsent: String? = null,
)