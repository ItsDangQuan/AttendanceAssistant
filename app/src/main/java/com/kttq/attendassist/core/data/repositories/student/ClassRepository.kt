package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.Class

interface ClassRepository {
    suspend fun getClassById(classId: String): Class

    // HACK: Use /api/v1/class/{class_id}/information
    // CONSIDER: Change to StudentRepository?
    suspend fun haveStudent(classId: String): Boolean

    // Currently, we do not have any endpoint for getting all classes of a student
    // Yes we do, /api/v1/student/all/class/enroll
    suspend fun getAllClass(): List<Class>
}