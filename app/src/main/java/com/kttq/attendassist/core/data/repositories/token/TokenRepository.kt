package com.kttq.attendassist.core.data.repositories.token

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    val accessToken: Flow<String?>
    val refreshToken: Flow<String?>

    suspend fun saveTokens(accessToken: String, refreshToken: String)
    suspend fun clearTokens()
}