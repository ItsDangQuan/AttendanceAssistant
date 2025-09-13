package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.model.Class
import javax.inject.Inject

class StudentClassRepositoryImpl @Inject constructor(
    private val classService: ClassService,
    private val studentService: StudentService
) : StudentClassRepository {
    override suspend fun getClassById(classId: Int): Class? {
        try {
            val response = classService.getClassInformation(classId)
            if (!response.isSuccessful) {
                return null
            }
            val classInformation = response.body() ?: return null
            return Class(classInformation)
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun haveStudent(classId: Int): Boolean? {
        try {
            val response = studentService.checkEnrollment(classId)
            if (!response.isSuccessful) {
                return null
            }
            val enrollmentCheckResponse = response.body() ?: return null
            return enrollmentCheckResponse.enrolled
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun getAllClass(): List<Class>? {
        try {
            val response = studentService.getAllClassEnrollments()
            if (!response.isSuccessful) {
                return null
            }
            val enrollmentCheckResponseList = response.body() ?: return null
            return enrollmentCheckResponseList.mapNotNull {
                return@mapNotNull this.getClassById(it.classId)
            }
        } catch (_: Exception) {
            return null
        }
    }
}