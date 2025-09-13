package com.kttq.attendassist.core.data.repositories.student

import android.util.Log
import com.kttq.attendassist.core.data.network.StudentService
import com.kttq.attendassist.core.model.Record
import javax.inject.Inject

class StudentRecordRepositoryImpl @Inject constructor(
    private val studentService: StudentService
) : StudentRecordRepository {
    override suspend fun getRecordByStudentId(studentId: String): List<Record>? {
        try {
            val response = studentService.getStudentRecords(studentId)

            Log.d("StudentRecordRepository", "Response: $response")
            if (!response.isSuccessful) {
                return null
            }
            val recordOutList = response.body() ?: return null
            return recordOutList.map {
                return@map Record(it)
            }
        } catch (_: Exception) {
            return null
        }
    }

    override suspend fun getCurrentStudentRecords(): List<Record>? {
        try {
            val response = studentService.getAttendanceRecords()
            if (!response.isSuccessful) {
                return null
            }
            val recordOutList = response.body() ?: return null
            return recordOutList.map {
                return@map Record(it)
            }
        } catch (_: Exception) {
            return null
        }
    }
}