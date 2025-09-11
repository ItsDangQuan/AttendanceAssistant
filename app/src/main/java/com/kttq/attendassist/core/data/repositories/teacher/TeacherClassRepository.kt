package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.model.StudentPerformance

interface TeacherClassRepository {
    // /api/v1/teacher/teaching_classes
    suspend fun getAllClass(): List<Class>?


    suspend fun getCurrentClass(): List<Class>
    suspend fun getPastClass(): List<Class>

    //  /api/v1/class/{classId}/information
    suspend fun getClassById(classId: String): Class?

    //  /api/v1/class/{classId}/students
    //  /api/v1/class/{classId}/records
    //  /api/v1/class/{classId}/sessions
    suspend fun getAllStudentInClass(classId: String): List<StudentPerformance>?
}