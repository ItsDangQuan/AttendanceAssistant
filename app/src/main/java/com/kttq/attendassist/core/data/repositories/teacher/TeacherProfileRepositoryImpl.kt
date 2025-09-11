package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.model.TeacherProfile
import javax.inject.Inject

class TeacherProfileRepositoryImpl @Inject constructor(
    private val teacherService: TeacherService
) : TeacherProfileRepository {
    override suspend fun getCurrentTeacherProfile(): TeacherProfile? {
        return try {
            val response = teacherService.getCurrentTeacher()
            if (!response.isSuccessful) {
                null
            }
            val teacherFull = response.body() ?: return null
            TeacherProfile(teacherFull)
        } catch (_: Exception) {
            null
        }
    }
}