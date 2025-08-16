package com.kttq.attendassist.core.data.network

import com.kttq.attendassist.core.data.network.dtos.RefreshToken
import com.kttq.attendassist.core.data.network.responses.Token
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import retrofit2.Response
import retrofit2.http.POST

//TODO: Fill in the blank
interface AuthApiService {
    @POST("/api/v1/auth/login")
    fun login( userLogin: UserLogin ): Response<Token>

    @POST("/api/v1/auth/refresh")
    fun refresh( refreshToken: RefreshToken): Response<Token>

    @POST("/api/v1/auth/logout")
    fun logout(): Response<Unit>
}