package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.ClassInformation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ClassService {
    @GET("/api/v1/class/{class_id}/information")
    suspend fun getClassInformation(@Path("class_id") classId: String): Response<ClassInformation>
}