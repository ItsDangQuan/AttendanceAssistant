package com.kttq.attendassist.core.data.repositories.teacher

import com.kttq.attendassist.core.model.Class
import com.kttq.attendassist.core.model.StudentPerformance

interface ClassRepository {
    // /api/v1/teacher/teaching_classes
    fun getAllClass(): List<Class>


    fun getCurrentClass(): List<Class>
    fun getPastClass(): List<Class>

    //  /api/v1/class/{classId}/information
    fun getClassById(classId: String): Class?

    //  /api/v1/class/{classId}/students
    //  /api/v1/class/{classId}/records
    //  /api/v1/class/{classId}/sessions
    fun getAllStudentInClass(classId: String): List<StudentPerformance>
}