package com.kttq.attendassist.core.data.repositories.auth

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import com.kttq.attendassist.core.domain.model.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @param:Named("AuthAuthService") private val authService: AuthService,
    private val tokenRepository: TokenRepository
) : AuthRepository {

    override fun getAuthState(): Flow<AuthState> =
        tokenRepository.accessToken
            .mapLatest { token ->        // mapLatest allows suspend
                if (token.isNullOrEmpty()) {
                    AuthState.UNAUTHENTICATED
                } else {
                    try {
                        val response = authService.role() // suspend call
                        if (response.isSuccessful) {
                            when (response.body()?.role?.lowercase()) {
                                "student" -> AuthState.AUTHENTICATED_STUDENT
                                "teacher" -> AuthState.AUTHENTICATED_TEACHER
                                else -> {
                                    tokenRepository.clearTokens()
                                    AuthState.UNAUTHENTICATED
                                }
                            }
                        } else {
                            tokenRepository.clearTokens()
                            AuthState.UNAUTHENTICATED
                        }
                    } catch (e: Exception) {
                        tokenRepository.clearTokens()
                        AuthState.UNAUTHENTICATED
                    }
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