package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.model.TeacherProfile

interface TeacherProfileRepository {
    // end point /api/v1/teacher/current
    suspend fun getCurrentUserAsTeacher() : TeacherProfile?
}