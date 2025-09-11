package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import com.kttq.attendassist.core.data.network.dtos.SessionInfo
import com.kttq.attendassist.core.data.network.dtos.SessionOut

data class TeacherSession(
    val sessionId: String,
    val classId: String,
    val courseId: String,
    val courseName: String,

    // TODO: May be removing this ?
    val startTime: String,
    val endTime: String,
) {
    constructor(sessionOut: SessionOut, classInformation: ClassInformation) : this(
        sessionOut.sessionId,
        sessionOut.classId,
        classInformation.courseId,
        classInformation.courseName,
        sessionOut.startTime,
        sessionOut.endTime
    )

    constructor(
        sessionInfo: SessionInfo,
        classId: String,
        classInformation: ClassInformation
    ) : this(
        sessionInfo.sessionId,
        classId,
        classInformation.courseId,
        classInformation.courseName,
        sessionInfo.startTime,
        sessionInfo.endTime
    )
}
