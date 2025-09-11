package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.Record

interface StudentRecordRepository {
    // Use /api/v1/student/{student_id}/records
    suspend fun getRecordByStudentId(studentId: String): List<Record>?

    // Use /api/v1/student/all/attendance_records to get of current student
    suspend fun getCurrentStudentRecords(): List<Record>?
}