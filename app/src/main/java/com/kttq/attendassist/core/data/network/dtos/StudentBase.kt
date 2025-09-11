package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StudentBase(
    @SerialName("student_id")
    val studentId: String
)