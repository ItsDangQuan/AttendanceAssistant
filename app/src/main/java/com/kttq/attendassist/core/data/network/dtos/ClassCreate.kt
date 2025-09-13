package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassCreate(
    @SerialName("course_id")
    val courseId: String,
    @SerialName("teacher_id")
    val teacherId: String,
    @SerialName("class_name")
    val className: String?,
    val semester: String?,
    val year: Int?
)