package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.Token
import com.kttq.attendassist.core.data.network.dtos.TokenRefresh
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//TODO: Fill in the blank
interface AuthService {
    @POST("/api/v1/auth/login")
    suspend fun login(@Body userLogin: UserLogin): Response<Token>

    @POST("/api/v1/auth/refresh")
    suspend fun refreshToken(@Body tokenRefresh: TokenRefresh): Response<Token>

    @POST("/api/v1/auth/logout")
    suspend fun logout(): Response<String?>

    @GET("/api/v1/auth/me/role")
    suspend fun getCurrentUserRole(): Response<String?>
}