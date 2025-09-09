package com.kttq.attendassist.core.data.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("course_id")
    val courseId: String,
    @SerialName("course_name")
    val courseName: String,
    @SerialName("department_id")
    val departmentId: String
)
