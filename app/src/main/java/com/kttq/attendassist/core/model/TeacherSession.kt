package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import com.kttq.attendassist.core.data.network.dtos.SessionInfo
import com.kttq.attendassist.core.data.network.dtos.SessionOut
import com.kttq.attendassist.core.util.DateTimeManager

data class TeacherSession(
    val sessionId: Int,
    val classId: Int,
    val className: String?,
    val courseId: String,
    val courseName: String,

    // TODO: May be removing this ?
    val startTime: String,
    val endTime: String,
) {
    constructor(sessionOut: SessionOut, classInformation: ClassInformation) : this(
        sessionOut.sessionId,
        sessionOut.classId,
        classInformation.className,
        classInformation.courseId,
        classInformation.courseName,
        DateTimeManager.serverUtcStringToLocalDisplay(sessionOut.startTime) ?: "",
        DateTimeManager.serverUtcStringToLocalDisplay(sessionOut.endTime) ?: "",
    )

    constructor(
        sessionInfo: SessionInfo,
        classInformation: ClassInformation
    ) : this(
        sessionInfo.sessionId,
        classInformation.classId,
        classInformation.className,
        classInformation.courseId,
        classInformation.courseName,
        DateTimeManager.serverUtcStringToLocalDisplay(sessionInfo.startTime) ?: "",
        DateTimeManager.serverUtcStringToLocalDisplay(sessionInfo.endTime) ?: "",
    )
}
