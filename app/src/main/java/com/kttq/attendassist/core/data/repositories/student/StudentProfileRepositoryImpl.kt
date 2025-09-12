package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.model.StudentProfile
import javax.inject.Inject

class StudentProfileRepositoryImpl @Inject constructor(
    private val studentService: StudentService
) : StudentProfileRepository {
    override suspend fun getCurrentStudentProfile(): StudentProfile? {
        try {
            val response = studentService.getCurrentStudent()
            if (!response.isSuccessful) {
                return null
            }
            val studentFull = response.body() ?: return null
            return StudentProfile(studentFull)
        } catch (_: Exception) {
            return null
        }
    }
}