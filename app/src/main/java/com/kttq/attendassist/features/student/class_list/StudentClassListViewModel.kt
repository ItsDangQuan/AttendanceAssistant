package com.kttq.attendassist.features.student.class_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.student.StudentClassRepository
import com.kttq.attendassist.core.model.Class // Changed import
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentClassListViewModel @Inject constructor(
    // TODO: Inject repository or use cases for fetching class data
    private val studentClassRepository: StudentClassRepository
) : ViewModel() {

    private val _currentClasses =
        MutableStateFlow<List<Class>?>(null) // Renamed and type updated
    val currentClasses: StateFlow<List<Class>?> =
        _currentClasses.asStateFlow() // Renamed and type updated

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _currentClasses.value = getCurrentClasses() // Renamed function call
            Log.d("StudentClassListViewModel", "Fetched current classes: ${_currentClasses.value}")
        }
    }

    private suspend fun getCurrentClasses(): List<Class>? { // Renamed and return type updated
        // TODO: Implement actual logic to fetch current classes from a repository/API
        // For now, returning an empty list or mock data
        return studentClassRepository.getAllClass()
    }
}
