package com.kttq.attendassist.core.data.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserOut(
    @SerialName("user_id")
    val userId: Int,
    val email: String,
    val role: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("department_id")
    val departmentId: String?,
    @SerialName("DOB")
    val dob: String?
)