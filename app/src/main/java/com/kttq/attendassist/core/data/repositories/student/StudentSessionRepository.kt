package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.StudentSession

interface StudentSessionRepository {
    // use /api/v1/session/{session_id}
    // and /api/v1/class/{class_id}/information
    suspend fun getStudentSessionById(sessionId: String): StudentSession

    // use /api/v1/student/roll_call
    suspend fun confirmAttendance(sessionId: String): Boolean
}