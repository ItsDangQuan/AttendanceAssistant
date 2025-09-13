package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.SessionService
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.data.network.dtos.AttendanceRequest
import com.kttq.attendassist.core.model.StudentSession
import javax.inject.Inject

class StudentSessionRepositoryImpl @Inject constructor(
    private val sessionService: SessionService,
    private val classService: ClassService,
    private val studentService: StudentService
) : StudentSessionRepository {
    override suspend fun getStudentSessionById(sessionId: Int): StudentSession? {
        try {
            val sessionOutResponse = sessionService.getSession(sessionId)
            if (!sessionOutResponse.isSuccessful) {
                return null
            }
            val sessionOut = sessionOutResponse.body() ?: return null
            val classInformationResponse = classService.getClassInformation(sessionOut.classId)
            if (!classInformationResponse.isSuccessful) {
                return null
            }
            val classInformation = classInformationResponse.body() ?: return null
            return StudentSession(sessionOut, classInformation)
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun confirmAttendance(sessionId: Int): Boolean {
        try {
            val attendanceRequest = AttendanceRequest(sessionId, "Present")
            val response = studentService.studentRollCall(attendanceRequest)
            return response.isSuccessful
        } catch (_: Exception) {
            return false
        }
    }
}