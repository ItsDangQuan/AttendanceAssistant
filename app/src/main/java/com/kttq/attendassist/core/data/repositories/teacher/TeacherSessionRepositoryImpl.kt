package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.SessionService
import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.data.network.dtos.SessionCreate
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.model.TeacherSession
import java.time.LocalTime
import javax.inject.Inject

class TeacherSessionRepositoryImpl @Inject constructor(
    private val teacherService: TeacherService,
    private val classService: ClassService,
    private val sessionService: SessionService
) : TeacherSessionRepository {
    override suspend fun getAllSession(): List<TeacherSession>? {
        try {
            val response = teacherService.getTeachingSessions()
            if (!response.isSuccessful) {
                return null
            }
            val sessionOutList = response.body() ?: return null
            return sessionOutList.mapNotNull {
                // TODO: In case of failed to fetch class information, skip one or discard all?
                //       Currently skip one.
                try {
                    val response = classService.getClassInformation(it.classId)
                    if (!response.isSuccessful) {
                        return@mapNotNull null
                    }
                    val classInformation = response.body() ?: return@mapNotNull null
                    return@mapNotNull TeacherSession(it, classInformation)
                } catch (_: Exception) {
                    return@mapNotNull null
                }
            }
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun getRecentSession(): List<TeacherSession> {
        // TODO("Not yet implemented. Filter by time how?")
        // Hmm, from my opinion, we can sort by the start_time of the teacher session, and may be, get the
        //  5 latest ones.
        return getAllSession()?.sortedBy { it.startTime }?.takeLast(5) ?: emptyList()
    }

    override suspend fun getSessionByClassId(classId: String): List<TeacherSession>? {
        try {
            val classInformationResponse = classService.getClassInformation(classId)
            if (!classInformationResponse.isSuccessful) {
                return null
            }
            val classInformation = classInformationResponse.body() ?: return null
            val sessionInfoResponse = classService.getClassSessions(classId)
            if (!sessionInfoResponse.isSuccessful) {
                return null
            }
            val sessionInfoList = sessionInfoResponse.body() ?: return null
            return sessionInfoList.map {
                return@map TeacherSession(it, classId, classInformation)
            }
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun createNewSession(classId: String, teacherId: String): TeacherSession {

        // TODO("Not yet implemented. What should go into start_time and end_time?")
        // The start_time should be "LocalTime.now(). However, I also do not know how to put into end_time
        // Currently, the end time has the same value as start time
        val sessionCreate = SessionCreate(
            startTime = LocalTime.now().toString(),
            endTime = LocalTime.now().toString(),
            classId = classId,
            teacherId = teacherId
        )

        val response = sessionService.createSession(sessionCreate)
        if (!response.isSuccessful) {
            throw Exception("Failed to create session")
        }
        val sessionOut = response.body() ?: throw Exception("Failed to create session")
        val classResponse = classService.getClassInformation(classId)
        if (!classResponse.isSuccessful) {
            throw Exception("Failed to create session")
        }
        val classInfo = classResponse.body() ?: throw Exception("Failed to create session")
        return TeacherSession(sessionOut, classInfo)

    }

    override suspend fun getStudentInSession(
        classId: String,
        sessionId: String
    ): List<StudentProfile> {
        TODO("Not yet implemented")
    }
}