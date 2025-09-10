package com.kttq.attendassist.core.data.repositories.auth

import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.model.AuthState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val authState: Flow<AuthState?>
    suspend fun login(userLogin: UserLogin): Boolean
    suspend fun logout()
    suspend fun sync()
}