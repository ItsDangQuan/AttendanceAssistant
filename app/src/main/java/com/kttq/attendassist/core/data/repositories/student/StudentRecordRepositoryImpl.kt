package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.model.Record
import javax.inject.Inject

class StudentRecordRepositoryImpl @Inject constructor(
    private val studentService: StudentService
) : StudentRecordRepository {
    override suspend fun getRecordByStudentId(studentId: String): List<Record>? {
        return try {
            val response = studentService.getStudentRecords(studentId)
            if (!response.isSuccessful) {
                null
            }
            val recordOutList = response.body() ?: return null
            recordOutList.map {
                Record(it)
            }
        } catch (_: Exception) {
            null
        }
    }

    override suspend fun getCurrentStudentRecords(): List<Record>? {
        return try {
            val response = studentService.getAttendanceRecords()
            if (!response.isSuccessful) {
                null
            }
            val recordOutList = response.body() ?: return null
            recordOutList.map {
                Record(it)
            }
        } catch (_: Exception) {
            null
        }
    }
}