package com.kttq.attendassist.core.data.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Record(
    @SerialName("record_id")
    val recordId: String,
    @SerialName("student_id")
    val studentId: String,
    @SerialName("status")
    val status: String,
    @SerialName("session_id")
    val session: String
)
