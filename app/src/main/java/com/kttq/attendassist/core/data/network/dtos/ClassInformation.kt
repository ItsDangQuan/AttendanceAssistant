package com.kttq.attendassist.core.data.network.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassInformation(
    @SerialName("class_id")
    val classId: String,
    @SerialName("course_id")
    val courseId: String,
    @SerialName("teacher_id")
    val teacherId: String,
    @SerialName("course_name")
    val courseName: String,
    @SerialName("teacher_name")
    val teacherName: String,
    @SerialName("department_id")
    val departmentId: String,
    @SerialName("department_name")
    val departmentName: String,
    val semester: String?,
    val year: Int?
)
