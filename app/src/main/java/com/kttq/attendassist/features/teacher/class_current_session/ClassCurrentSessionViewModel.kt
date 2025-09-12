package com.kttq.attendassist.features.teacher.class_current_session

import com.kttq.attendassist.core.model.StudentProfile


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.ble.advertiser.BleAdvertiser
import com.kttq.attendassist.core.data.repositories.teacher.TeacherClassRepository
import com.kttq.attendassist.core.model.StudentPerformance
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassStudentListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teacherClassRepository: TeacherClassRepository,
    private val bleAdvertiser: BleAdvertiser
) : ViewModel() {
    val sessionId = savedStateHandle.toRoute<Destination.Teacher.ClassCurrentSession>().sessionId

    private val _studentsList = MutableStateFlow<List<StudentProfile>>(emptyList())
    val studentsList = _studentsList.asStateFlow()

    private val _currentsStudentsList = MutableStateFlow<List<StudentProfile>>(emptyList())
    val currentsStudentsList = _currentsStudentsList.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            // TODO: Fetch the data from the repo.
            //  Now just mock the data.
        }
    }
    fun stopCurrentAttendanceSession() {
        viewModelScope.launch {
                bleAdvertiser.stopAdvertising()
        }
     }

}