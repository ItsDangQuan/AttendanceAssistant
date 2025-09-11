package com.kttq.attendassist.core.model

import com.kttq.attendassist.core.data.network.dtos.TeacherFull


data class TeacherProfile(
    val teacherId: String,
    val email: String,
    val firstName: String,
    val lastName: String,

    val dob: String?,
    val departmentId: String?,
    // TODO: Consider removing this, database does not store this information.
    // val phone: String?,
) {
    constructor(teacherFull: TeacherFull) : this(
        teacherFull.teacherId,
        teacherFull.email,
        teacherFull.firstName,
        teacherFull.lastName,
        teacherFull.dob,
        teacherFull.departmentId,
    )
}
