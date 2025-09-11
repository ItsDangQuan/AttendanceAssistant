package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.StudentFull

data class StudentProfile(
    val studentId: String,
    val email: String,
    val firstName: String,
    val lastName: String,


    // Currently, we need only 4 fields from the Student table.
    // val dob: String,
    // val schoolYear: String,
    // val department_id: String,
) {
    constructor(studentFull: StudentFull) : this(
        studentFull.studentId,
        studentFull.email,
        studentFull.firstName,
        studentFull.lastName
    )
}