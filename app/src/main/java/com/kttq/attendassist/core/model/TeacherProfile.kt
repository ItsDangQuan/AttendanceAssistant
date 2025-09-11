package com.kttq.attendassist.core.model


data class TeacherProfile(
    val teacherId: String,
    val email: String,
    val firstName: String,
    val lastName: String,

    val DOB: String?,
    val departmentId: String?,
    val phone: String?,
)
