package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.ClassInformation

data class Class(
    val classId: String,
    val semester: String,
    val year: Int,
    val courseId: String,
    val courseName: String,
    val teacherId: String,
    val teacherName: String,
) {
    constructor(classInformation: ClassInformation) : this(
        classInformation.classId,
        // classInformation.semester,
        // classInformation.year,
        "N/A",
        2023,
        classInformation.courseId,
        classInformation.courseName,
        classInformation.teacherId,
        classInformation.teacherName
    )
}
