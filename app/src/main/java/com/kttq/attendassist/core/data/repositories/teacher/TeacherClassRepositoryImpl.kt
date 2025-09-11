package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.model.StudentPerformance
import javax.inject.Inject

class TeacherClassRepositoryImpl @Inject constructor(
    private val teacherService: TeacherService,
    private val classService: ClassService
) : TeacherClassRepository {
    override suspend fun getAllClass(): List<Class>? {
        return try {
            val response = teacherService.getTeachingClasses()
            if (!response.isSuccessful) {
                null
            }
            val classInformationList = response.body() ?: return null
            classInformationList.map {
                Class(it)
            }
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getCurrentClass(): List<Class> {
        TODO("Not yet implemented. No API?")
    }

    override suspend fun getPastClass(): List<Class> {
        TODO("Not yet implemented. No API?")
    }

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

    override suspend fun getAllStudentInClass(classId: String): List<StudentPerformance>? {
        TODO("Not yet implemented. No API to get full student information from student ID.")
    }
}