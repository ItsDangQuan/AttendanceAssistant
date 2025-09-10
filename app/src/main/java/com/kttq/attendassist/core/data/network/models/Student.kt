package com.kttq.attendassist.core.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    @SerialName("ID")
    val id: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("student_id")
    val studentId: String,



    // This is beyond the scope of the Student table.
    @SerialName("name")
    val name: String,
)
