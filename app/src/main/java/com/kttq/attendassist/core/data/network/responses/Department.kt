package com.kttq.attendassist.core.data.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Department(
    @SerialName("department_id")
    val departmentId: String,
    @SerialName("department_name")
    val departmentName: String,
)
