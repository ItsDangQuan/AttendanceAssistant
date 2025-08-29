package com.kttq.attendassist

import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

// src/androidTest/java/.../test/FakeTokenRepository.kt
class FakeTokenRepository @Inject constructor() : TokenRepository {
    private val _access = MutableStateFlow<String?>(null)
    private val _refresh = MutableStateFlow<String?>(null)

    override val accessToken: Flow<String?> = _access
    override val refreshToken: Flow<String?> = _refresh

    // suspend because your interface defines suspend
    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        _access.value = accessToken
        _refresh.value = refreshToken
    }

    override suspend fun clearTokens() {
        _access.value = null
        _refresh.value = null
    }

    suspend fun currentAccessToken(): String? = accessToken.first()
    suspend fun currentRefreshToken(): String? = refreshToken.first()
}

