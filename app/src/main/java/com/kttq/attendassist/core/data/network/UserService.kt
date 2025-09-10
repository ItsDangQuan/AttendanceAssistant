package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.UserCreate
import com.kttq.attendassist.core.data.network.dtos.UserOut
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @GET("/api/v1/user")
    suspend fun getUsers(): Response<List<UserOut>>

    @POST("/api/v1/user")
    suspend fun createUser(@Body userCreate: UserCreate): Response<UserOut>

    @GET("/api/v1/user/current")
    suspend fun getCurrentUser(): Response<UserOut>
}