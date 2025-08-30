package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.RefreshToken
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.data.network.responses.Token
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

//TODO: Fill in the blank
interface AuthService {
    // Should we put the Body annotation here or in the data class?
    @POST("/api/v1/auth/login")
    suspend fun login(@Body userLogin: UserLogin): Response<Token>

    @POST("/api/v1/auth/refresh")
    suspend fun refresh(@Body refreshToken: RefreshToken): Response<Token>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): Response<Unit>
}