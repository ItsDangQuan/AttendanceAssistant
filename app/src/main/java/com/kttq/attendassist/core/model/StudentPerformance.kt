package com.kttq.attendassist.core.model

data class StudentPerformance(
    val userId: String,
    val studentId: String,
    val studentName: String,

    val classId: String,

    val studentAttendance: List<Record>

    // This is beyond the scope of the Student table.
)
