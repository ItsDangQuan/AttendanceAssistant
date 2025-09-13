package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import com.kttq.attendassist.core.data.network.dtos.SessionOut
import com.kttq.attendassist.core.util.DateTimeManager

data class StudentSession(
    val sessionId: Int,
    val classId: Int,
    val teacherId: String,

    val teacherName: String,
    val courseId: String,
    val courseName: String,

    // TODO: Consider to remove this field.
    val startTime: String,
    val endTime: String,
) {
    constructor(sessionOut: SessionOut, classInformation: ClassInformation) : this(
        sessionOut.sessionId,
        sessionOut.classId,
        sessionOut.teacherId,
        classInformation.teacherName,
        classInformation.courseId,
        classInformation.courseName,
        DateTimeManager.serverUtcStringToLocalDisplay(sessionOut.startTime) ?: "",
        DateTimeManager.serverUtcStringToLocalDisplay(sessionOut.endTime) ?: "",
    )
}

