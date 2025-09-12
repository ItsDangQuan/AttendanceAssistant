package com.kttq.attendassist.features.teacher.class_past_session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.data.repositories.teacher.TeacherSessionRepository
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassPastSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teacherSessionRepository: TeacherSessionRepository,
) : ViewModel() {
    val classId = savedStateHandle.toRoute<Destination.Teacher.ClassPastSession>().classId

    private val _sessionList = MutableStateFlow<List<TeacherSession>?>(null)
    val sessionList = _sessionList.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {

            // TODO: Fetch the data from the repo.
            //  Now just mock the data.
            _sessionList.value = teacherSessionRepository.getSessionByClassId(classId)
        }
    }
}