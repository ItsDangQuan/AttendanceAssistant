package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserCreate(
    val email: String,
    val role: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("department_id")
    val departmentId: String?,
    @SerialName("DOB")
    val dob: String?,
    val password: String,
    @SerialName("student_id")
    val studentId: String?,
    @SerialName("school_year")
    val schoolYear: String?,
)
