package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import com.kttq.attendassist.core.data.network.dtos.SessionOut
import com.kttq.attendassist.core.data.network.dtos.TeacherFull
import retrofit2.Response
import retrofit2.http.GET

interface TeacherService {
    @GET("/api/v1/teacher/current")
    suspend fun getCurrentTeacher(): Response<TeacherFull>

    @GET("/api/v1/teacher/teaching_sessions")
    suspend fun getTeachingSessions(): Response<List<SessionOut>>

    @GET("/api/v1/teacher/teaching_classes")
    suspend fun getTeachingClasses(): Response<List<ClassInformation>>

}