package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DepartmentBase(
    @SerialName("department_id")
    val departmentId: String,
    @SerialName("department_name")
    val departmentName: String,
)
