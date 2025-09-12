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
    override suspend fun getStudentSessionById(sessionId: String): StudentSession? {
        return try {
            val sessionOutResponse = sessionService.getSession(sessionId)
            if (!sessionOutResponse.isSuccessful) {
                null
            }
            val sessionOut = sessionOutResponse.body() ?: return null
            val classInformationResponse = classService.getClassInformation(sessionOut.classId)
            if (!classInformationResponse.isSuccessful) {
                null
            }
            val classInformation = classInformationResponse.body() ?: return null
            StudentSession(sessionOut, classInformation)
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun confirmAttendance(sessionId: String): Boolean {
        return try {
            val attendanceRequest = AttendanceRequest(sessionId, "Present")
            val response = studentService.studentRollCall(attendanceRequest)
            return response.isSuccessful
        } catch (_: Exception) {
            false
        }
    }
}