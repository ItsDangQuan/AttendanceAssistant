package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.StudentProfile

interface StudentProfileRepository {
    // use /api/v1/student/current
    suspend fun getCurrentStudentProfile(): StudentProfile?
}