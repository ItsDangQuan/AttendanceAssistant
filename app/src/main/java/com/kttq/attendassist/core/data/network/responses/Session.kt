package com.kttq.attendassist.core.data.network.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Session(
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("class_id")
    val classId: String,
    @SerialName("start_time")
    val startTime: Long,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("teacher_id")
    val teacherId: String,

    //This is beyond the scope of the session table
    @SerialName("class_name")
    val className: String,
    @SerialName("teacher_name")
    val teacherName: String,
    @SerialName("course_id")
    val courseId: String,
    @SerialName("course_name")
    val courseName: String
)
