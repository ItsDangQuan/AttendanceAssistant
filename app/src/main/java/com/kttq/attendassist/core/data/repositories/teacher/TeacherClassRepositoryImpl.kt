package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.data.network.ClassService
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.data.network.TeacherService
import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.model.StudentPerformance
import com.kttq.attendassist.core.model.StudentProfile
import javax.inject.Inject

class TeacherClassRepositoryImpl @Inject constructor(
    private val teacherService: TeacherService,
    private val classService: ClassService,
    private val studentService: StudentService
) : TeacherClassRepository {
    override suspend fun getAllClass(): List<Class>? {
        try {
            val response = teacherService.getTeachingClasses()
            if (!response.isSuccessful) {
                return null
            }
            val classInformationList = response.body() ?: return null
            return classInformationList.map {
                Class(it)
            }
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun getCurrentClass(): List<Class> {
        try {
            val response = teacherService.getTeachingClasses()
            if (!response.isSuccessful) {
                return emptyList()
            }
            val classInformationList = response.body() ?: return emptyList()
            return classInformationList.map {
                Class(it)
            }
        } catch (_: Exception) {
            return emptyList()
        }
    }

    override suspend fun getPastClass(): List<Class> {
        try {
            val response = teacherService.getTeachingClasses()
            if (!response.isSuccessful) {
                return emptyList()
            }
            val classInformationList = response.body() ?: return emptyList()
            return classInformationList.map {
                return@map Class(it)
            }
        } catch (_: Exception) {
            return emptyList()
        }
    }

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

    override suspend fun getAllStudentPerformanceInClass(classId: Int): List<StudentPerformance>? {
        try {
            val response = classService.getClassStudents(classId)
            if (!response.isSuccessful) {
                return null
            }
            val studentBaseList = response.body() ?: return null
            return studentBaseList.mapNotNull {
                val studentFullResponse = studentService.getFullStudent(it.studentId)
                if (!studentFullResponse.isSuccessful) {
                    return@mapNotNull null
                }
                val studentFull = studentFullResponse.body() ?: return@mapNotNull null
                val recordOutListResponse = studentService.getStudentRecords(it.studentId)
                if (!recordOutListResponse.isSuccessful) {
                    return@mapNotNull null
                }
                val recordOutList = recordOutListResponse.body() ?: return@mapNotNull null
                return@mapNotNull StudentPerformance(studentFull, classId, recordOutList)
            }
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun getAllStudentProfileInClass(classId: Int): List<StudentProfile>? {
        try {
            val response = classService.getClassStudents(classId)
            if (!response.isSuccessful) {
                return null
            }
            val studentBaseList = response.body() ?: return null
            return studentBaseList.mapNotNull {
                val studentFullResponse = studentService.getFullStudent(it.studentId)
                if (!studentFullResponse.isSuccessful) {
                    return@mapNotNull null
                }
                val studentFull = studentFullResponse.body() ?: return@mapNotNull null
                return@mapNotNull StudentProfile(studentFull)
            }
        } catch (_: Exception) {
            return null
        }
    }
}