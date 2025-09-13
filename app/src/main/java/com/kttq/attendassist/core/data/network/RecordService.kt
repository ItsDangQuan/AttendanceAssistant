package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.RecordCreate
import com.kttq.attendassist.core.data.network.dtos.RecordOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecordService {
    @GET("/api/v1/record")
    suspend fun getRecords(): Response<List<RecordOut>>

    @POST("/api/v1/record")
    suspend fun createRecord(@Body recordCreate: RecordCreate): Response<RecordOut>

    @GET("/api/v1/record/{session_id}")
    suspend fun getSessionRecords(@Path("session_id") sessionId: Int): Response<List<RecordOut>>
}