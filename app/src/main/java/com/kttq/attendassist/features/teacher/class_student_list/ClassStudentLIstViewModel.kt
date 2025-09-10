package com.kttq.attendassist.features.teacher.class_student_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.data.network.models.Student
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassStudentListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,

) : ViewModel() {
    val classId = savedStateHandle.toRoute<Destination.Teacher.ClassStudentList>().classId

    val _studentsList = MutableStateFlow<List<Student>>(emptyList())
    val studentsList = _studentsList.asStateFlow()

    init {
        fetchData()
    }
    fun fetchData() {
        viewModelScope.launch {

            // TODO: Fetch the data from the repo.
            //  Now just mock the data.
            _studentsList.value = emptyList()
        }
    }
}