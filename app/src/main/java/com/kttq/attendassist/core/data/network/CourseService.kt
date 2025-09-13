package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.CourseBase
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CourseService {
    @GET("/api/v1/course")
    suspend fun getCourses(): Response<List<CourseBase>>

    @POST("/api/v1/course")
    suspend fun createCourse(@Body courseBase: CourseBase): Response<CourseBase>
}