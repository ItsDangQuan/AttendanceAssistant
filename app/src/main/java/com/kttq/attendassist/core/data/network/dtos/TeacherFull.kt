package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeacherFull(
    @SerialName("teacher_id")
    val teacherId: String,
    @SerialName("email")
    val email: String,
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("DOB")
    val DOB: String?,
    @SerialName("department_id")
    val departmentId: String?
)
