package com.kttq.attendassist.features.teacher.class_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.network.models.Class
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherClassListViewModel @Inject constructor(

) : ViewModel() {
    private val _currentClasses = MutableStateFlow<List<Class>>(emptyList()) // Renamed and type updated
    val currentClasses: StateFlow<List<Class>> = _currentClasses.asStateFlow() // Renamed and type updated

    private val _pastClasses = MutableStateFlow<List<Class>>(emptyList()) // Renamed and type updated
    val pastClasses: StateFlow<List<Class>> = _pastClasses.asStateFlow() // Renamed and type updated

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _currentClasses.value = getCurrentClasses() // Renamed function call
            _pastClasses.value = getPastClasses() // Renamed function call
        }
    }

    private suspend fun getCurrentClasses(): List<Class> { // Renamed and return type updated
        // TODO: Implement actual logic to fetch current classes from a repository/API
        // For now, returning an empty list or mock data
        return emptyList()
    }

    private suspend fun getPastClasses(): List<Class> { // Renamed and return type updated
        // TODO: Implement actual logic to fetch past classes from a repository/API
        // For now, returning an empty list or mock data
        return emptyList()
    }
}