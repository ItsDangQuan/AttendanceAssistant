package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.RecordService
import com.kttq.attendassist.core.data.network.SessionService
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.data.network.dtos.SessionCreate
import com.kttq.attendassist.core.model.StudentProfile
import com.kttq.attendassist.core.model.TeacherSession
import com.kttq.attendassist.core.util.DateTimeManager
import javax.inject.Inject

class TeacherSessionRepositoryImpl @Inject constructor(
    private val teacherService: TeacherService,
    private val classService: ClassService,
    private val sessionService: SessionService,
    private val recordService: RecordService,
    private val studentService: StudentService
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
                .reversed()
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun getSessionBySessionId(sessionId: Int): TeacherSession? {
        val response = sessionService.getSession(sessionId)
        if (!response.isSuccessful) {
            return null
        } else if (response.body() == null) {
            return null
        } else {
            val sessionOut = response.body()
            if (sessionOut == null) {
                return null
            }
            try {
                val response = classService.getClassInformation(sessionOut.classId)
                if (!response.isSuccessful) {
                    return null
                }
                val classInformation = response.body() ?: return null
                return TeacherSession(sessionOut, classInformation)
            } catch (_: Exception) {
                return null
            }
        }
    }

    override suspend fun getRecentSession(n: Int): List<TeacherSession>? {
        // TODO("Not yet implemented. Filter by time how?")
        // Hmm, from my opinion, we can sort by the start_time of the teacher session, and may be, get the
        //  5 latest ones.
        return getAllSession()?.sortedBy { it.startTime }?.takeLast(n)?.reversed()
    }

    override suspend fun getSessionByClassId(classId: Int): List<TeacherSession>? {
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
            return sessionInfoList
                .map {
                    return@map TeacherSession(it, classInformation)
                }
                .reversed()
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun createNewSession(classId: Int, teacherId: String): TeacherSession {

        // TODO("Not yet implemented. What should go into start_time and end_time?")
        // The start_time should be "LocalTime.now(). However, I also do not know how to put into end_time
        // Currently, the end time has the same value as start time
        val currentDateTime: String = DateTimeManager.localTimeAsFormattedString()
        val sessionCreate = SessionCreate(
            startTime = currentDateTime,
            endTime = currentDateTime,
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
        classId: Int,
        sessionId: Int
    ): List<StudentProfile>? {
        try {
            val response = recordService.getSessionRecords(sessionId)
            if (response.code() == 404)
                return emptyList()
            if (!response.isSuccessful) {
                return null
            }
            val recordList = response.body() ?: return null
            return recordList.mapNotNull {
                try {
                    val response = studentService.getFullStudent(it.studentId)
                    if (!response.isSuccessful) {
                        return@mapNotNull null
                    }
                    val studentFull = response.body() ?: return@mapNotNull null
                    return@mapNotNull StudentProfile(studentFull)
                } catch (_: Exception) {
                    return@mapNotNull null
                }
            }

        } catch (_: Exception) {
            return null
        }
    }

}