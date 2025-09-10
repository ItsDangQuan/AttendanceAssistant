package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecordOut(
    @SerialName("student_id")
    val studentId: String,
    @SerialName("status")
    val status: String,
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("record_id")
    val recordId: String,
)
