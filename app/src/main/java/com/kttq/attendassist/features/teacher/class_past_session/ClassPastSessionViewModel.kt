package com.kttq.attendassist.features.teacher.class_past_session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.kttq.attendassist.core.data.repositories.teacher.TeacherClassRepository
import com.kttq.attendassist.core.data.repositories.teacher.TeacherSessionRepository
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.navigation.Destination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassPastSessionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val teacherSessionRepository: TeacherSessionRepository,
    private val teacherClassRepository: TeacherClassRepository
) : ViewModel() {
    val sessionId = savedStateHandle.toRoute<Destination.Teacher.ClassPastSession>().sessionId

    private val _session = MutableStateFlow<TeacherSession?>(null)
    val session: StateFlow<TeacherSession?> = _session.asStateFlow()

    private val _studentsList = MutableStateFlow<List<StudentProfile>?>(null)
    val studentsList: StateFlow<List<StudentProfile>?> = _studentsList.asStateFlow()

    private val _currentsStudentsList = MutableStateFlow<List<StudentProfile>?>(null)
    val currentsStudentsList: StateFlow<List<StudentProfile>?> = _currentsStudentsList.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {

            // TODO: Fetch the data from the repo.
            //  Now just mock the data.

            val session = teacherSessionRepository.getSessionBySessionId(sessionId)

            if (session == null) return@launch
            _session.value = session
            _studentsList.value =
                teacherClassRepository.getAllStudentProfileInClass(session.classId)
            _currentsStudentsList.value = teacherSessionRepository.getStudentInSession(session.classId, session.sessionId)

        }
    }
}