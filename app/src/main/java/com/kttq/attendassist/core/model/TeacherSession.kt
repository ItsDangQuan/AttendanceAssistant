package com.kttq.attendassist.core.model

data class TeacherSession(
    val sessionId: String,
    val classId: String,
    val courseId: String,
    val courseName: String,

    // TODO: May be removing this ?
    val startTime: String,
    val endTime: String,
)
