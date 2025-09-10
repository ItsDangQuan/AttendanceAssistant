package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttendanceRequest(
    @SerialName("session_id")
    val sessionId: String,
    val status: String,
)
