package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EnrollmentBase(
    @SerialName("student_id")
    val studentId: String,
    @SerialName("class_id")
    val classId: Int,
)