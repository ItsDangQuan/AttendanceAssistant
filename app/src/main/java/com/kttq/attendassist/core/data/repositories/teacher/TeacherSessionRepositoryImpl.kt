package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.model.TeacherSession
import javax.inject.Inject

class TeacherSessionRepositoryImpl @Inject constructor(
    private val teacherService: TeacherService,
    private val classService: ClassService
) : TeacherSessionRepository {
    override suspend fun getAllSession(): List<TeacherSession>? {
        return try {
            val response = teacherService.getTeachingSessions()
            if (!response.isSuccessful) {
                null
            }
            val sessionOutList = response.body() ?: return null
            sessionOutList.mapNotNull {
                // TODO: In case of failed to fetch class information, skip one or discard all?
                //       Currently skip one.
                try {
                    val response = classService.getClassInformation(it.classId)
                    if (!response.isSuccessful) {
                        null
                    }
                    val classInformation = response.body() ?: return@mapNotNull null
                    TeacherSession(it, classInformation)
                } catch (_: Exception) {
                    null
                }
            }
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getRecentSession(): List<TeacherSession> {
        TODO("Not yet implemented. Filter by time how?")
    }

    override suspend fun getSessionByClassId(classId: String): List<TeacherSession>? {
        return try {
            val classInformationResponse = classService.getClassInformation(classId)
            if (!classInformationResponse.isSuccessful) {
                null
            }
            val classInformation = classInformationResponse.body() ?: return null
            val sessionInfoResponse = classService.getClassSessions(classId)
            if (!sessionInfoResponse.isSuccessful) {
                null
            }
            val sessionInfoList = sessionInfoResponse.body() ?: return null
            sessionInfoList.map {
                TeacherSession(it, classId, classInformation)
            }
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun createNewSession(classId: String, teacherId: String): TeacherSession {
        TODO("Not yet implemented. What should go into start_time and end_time?")
    }
}