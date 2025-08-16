package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(
    val email: String,
    val password: String,
)
