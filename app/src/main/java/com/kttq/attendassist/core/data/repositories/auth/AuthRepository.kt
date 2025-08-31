package com.kttq.attendassist.core.data.repositories.auth

import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.domain.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAuthState(): Flow<AuthState>
    suspend fun login(userLogin: UserLogin): Flow<AuthState>
    suspend fun logout()
}