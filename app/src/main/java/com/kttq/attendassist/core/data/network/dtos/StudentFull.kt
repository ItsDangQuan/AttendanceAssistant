package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentFull(
    @SerialName("student_id")
    val studentId: String,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("DOB")
    val dob: String?,
    @SerialName("school_year")
    val schoolYear: String?,
    @SerialName("department_id")
    val departmentId: String?,
)
