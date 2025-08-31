package com.kttq.attendassist.core.data.repositories.auth

import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import com.kttq.attendassist.core.domain.model.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @param:Named("AuthAuthService") private val authService: AuthService,
    private val tokenRepository: TokenRepository
) : AuthRepository {
    override fun getAuthState(): Flow<AuthState> = flow {
        val accessToken = tokenRepository.accessToken.firstOrNull()
        if (accessToken.isNullOrEmpty()) {
            emit(AuthState.UNAUTHENTICATED)
            return@flow
        }

        emit(AuthState.LOADING_ROLE)
        try {
            val response = authService.role()
            if (response.isSuccessful) {
                val roleResponse = response.body()
                when (roleResponse?.role?.lowercase()) {
                    "student" -> emit(AuthState.AUTHENTICATED_STUDENT)
                    "teacher" -> emit(AuthState.AUTHENTICATED_TEACHER)
                    else -> {
                        tokenRepository.clearTokens()
                        emit(AuthState.UNAUTHENTICATED)
                    }
                }
            } else {
                tokenRepository.clearTokens()
                emit(AuthState.UNAUTHENTICATED)
            }
        } catch (e: Exception) {
            tokenRepository.clearTokens()
            emit(AuthState.UNAUTHENTICATED)
        }
    }

    override suspend fun login(userLogin: UserLogin): Flow<AuthState> = flow {
        try {
            val response = authService.login(userLogin)
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!
                tokenRepository.saveTokens(
                    token.accessToken,
                    token.refreshToken
                )
                emit(AuthState.LOADING_ROLE)
                val roleResponse = authService.role()
                if (roleResponse.isSuccessful && roleResponse.body() != null) {
                    when (roleResponse.body()!!.role.lowercase()) {
                        "student" -> emit(AuthState.AUTHENTICATED_STUDENT)
                        "teacher" -> emit(AuthState.AUTHENTICATED_TEACHER)
                        else -> {
                            tokenRepository.clearTokens()
                            emit(AuthState.UNAUTHENTICATED)
                        }
                    }
                } else {
                    tokenRepository.clearTokens()
                    emit(AuthState.UNAUTHENTICATED)
                }
            } else {
                tokenRepository.clearTokens()
                emit(AuthState.UNAUTHENTICATED)
            }
        } catch (e: Exception) {
            emit(AuthState.UNAUTHENTICATED)
        }
    }

    override suspend fun logout() {
        tokenRepository.clearTokens()
    }
}