package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionInfo(
    @SerialName("session_id")
    val sessionId: Integer,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("end_time")
    val endTime: String
)