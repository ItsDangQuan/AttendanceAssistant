package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseBase(
    @SerialName("course_id")
    val courseId: String,
    @SerialName("course_name")
    val courseName: String,
    @SerialName("department_id")
    val departmentId: String?,
)