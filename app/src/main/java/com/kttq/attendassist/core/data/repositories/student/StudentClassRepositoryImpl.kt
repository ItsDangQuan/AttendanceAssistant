package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.model.Class
import javax.inject.Inject

class StudentClassRepositoryImpl @Inject constructor(
    private val classService: ClassService,
    private val studentService: StudentService
) : StudentClassRepository {
    override suspend fun getClassById(classId: String): Class? {
        return try {
            val response = classService.getClassInformation(classId)
            if (!response.isSuccessful) {
                null
            }
            val classInformation = response.body() ?: return null
            Class(classInformation)
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun haveStudent(classId: String): Boolean? {
        return try {
            val response = studentService.checkEnrollment(classId)
            if (!response.isSuccessful) {
                null
            }
            val enrollmentCheckResponse = response.body() ?: return null
            enrollmentCheckResponse.enrolled
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getAllClass(): List<Class>? {
        return try {
            val response = studentService.getAllClassEnrollments()
            if (!response.isSuccessful) {
                null
            }
            val enrollmentCheckResponseList = response.body() ?: return null
            enrollmentCheckResponseList.mapNotNull {
                this.getClassById(it.classId)
            }
        } catch (_: Exception) {
            null
        }
    }
}