package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.EnrollmentBase
import com.kttq.attendassist.core.data.network.dtos.EnrollmentOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EnrollmentService {
    @GET("/api/v1/enrollment")
    suspend fun getEnrollments(): Response<EnrollmentBase>

    @POST("/api/v1/enrollment")
    suspend fun createEnrollment(@Body enrollmentBase: EnrollmentBase): Response<EnrollmentOut>
}