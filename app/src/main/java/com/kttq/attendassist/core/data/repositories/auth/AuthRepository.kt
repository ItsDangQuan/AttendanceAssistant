package com.kttq.attendassist.core.data.repositories.auth

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun refreshToken(): Result<Unit>
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
}