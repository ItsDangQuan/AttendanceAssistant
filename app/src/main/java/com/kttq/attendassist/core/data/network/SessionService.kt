package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.SessionCreate
import com.kttq.attendassist.core.data.network.dtos.SessionOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SessionService {
    @GET("/api/v1/session")
    suspend fun getSessions(): Response<List<SessionOut>>

    @POST("/api/v1/session")
    suspend fun createSession(@Body sessionCreate: SessionCreate): Response<SessionOut>

    @GET("/api/v1/session/current_session")
    suspend fun getCurrentSession(@Query("class_id") classId: String): Response<List<SessionOut>>
}