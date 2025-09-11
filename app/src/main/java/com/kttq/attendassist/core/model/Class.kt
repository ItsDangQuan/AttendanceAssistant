package com.kttq.attendassist.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Class(
    @SerialName("class_id")
    val classId: String,
    @SerialName("course_id")
    val courseId: String,
    @SerialName("semester")
    val semester: String,
    @SerialName("year")
    val year: Int,
    @SerialName("teacher_id")
    val teacherId: String,

    @SerialName("teacher_name")
    val teacherName: String,

    // This is beyond of the scope of `class` table in database
    @SerialName("course_name")
    val courseName: String,
)
