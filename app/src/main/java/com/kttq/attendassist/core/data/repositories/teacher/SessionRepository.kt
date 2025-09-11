package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.model.TeacherSession

interface SessionRepository {

    // Use the api /api/v1/teacher/teaching_sessions/
    suspend fun getAllSession(): List<TeacherSession>
    // Same as above, but filter by time, ig ?
    suspend fun getRecentSession(): List<TeacherSession>
}