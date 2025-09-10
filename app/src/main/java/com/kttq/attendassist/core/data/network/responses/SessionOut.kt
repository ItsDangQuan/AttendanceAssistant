package com.kttq.attendassist.core.data.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionOut(

    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("class_id")
    val classId: String,
    @SerialName("teacher_id")
    val teacherId: String,
    @SerialName("session_id")
    val sessionId: String,
)
