package com.kttq.attendassist.core.data.repositories.auth

import android.util.Log
import androidx.activity.result.launch
import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import com.kttq.attendassist.core.domain.model.AuthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

class AuthRepositoryImpl @Inject constructor(
    @param:Named("AuthAuthService") private val authService: AuthService,
    private val tokenRepository: TokenRepository,
) : AuthRepository {
    private val externalScope: CoroutineScope = CoroutineScope(Dispatchers.Default) // Inject or provide a scope
//    override fun getAuthState(): Flow<AuthState> = flow {
//        val accessToken = tokenRepository.accessToken.firstOrNull()
//        if (accessToken.isNullOrEmpty()) {
//            emit(AuthState.UNAUTHENTICATED)
//            return@flow
//        }
//
//        emit(AuthState.LOADING_ROLE)
//        try {
//            val response = authService.role()
//            if (response.isSuccessful) {
//                val roleResponse = response.body()
//                when (roleResponse?.role?.lowercase()) {
//                    "student" -> emit(AuthState.AUTHENTICATED_STUDENT)
//                    "teacher" -> emit(AuthState.AUTHENTICATED_TEACHER)
//                    else -> {
//                        tokenRepository.clearTokens()
//                        emit(AuthState.UNAUTHENTICATED)
//                    }
//                }
//            } else {
//                tokenRepository.clearTokens()
//                emit(AuthState.UNAUTHENTICATED)
//            }
//        } catch (e: Exception) {
//            tokenRepository.clearTokens()
//            emit(AuthState.UNAUTHENTICATED)
//        }
//    }
//
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
                // val roleResponse = authService.role()
                // if (roleResponse.isSuccessful && roleResponse.body() != null) {
                //     when (roleResponse.body()!!.role.lowercase()) {
                //         "student" -> emit(AuthState.AUTHENTICATED_STUDENT)
                //         "teacher" -> emit(AuthState.AUTHENTICATED_TEACHER)
                //         else -> {
                //             tokenRepository.clearTokens()
                //             emit(AuthState.UNAUTHENTICATED)
                //         }
                //     }
                // } else {
                //     tokenRepository.clearTokens()
                //     emit(AuthState.UNAUTHENTICATED)
                // }
                fetchUserRole()
                emit(_authState.value)
            } else {
                tokenRepository.clearTokens()
                emit(AuthState.UNAUTHENTICATED)
            }
        } catch (e: Exception) {
            emit(AuthState.UNAUTHENTICATED)
        }
    }

    private val _authState = MutableStateFlow(AuthState.UNAUTHENTICATED)
    override fun getAuthState(): Flow<AuthState> = _authState.asStateFlow()

    init {
        tokenRepository.accessToken
            .onEach { token ->
                if(token.isNullOrEmpty()) {
                    _authState.value = AuthState.UNAUTHENTICATED
                } else if (_authState.value == AuthState.UNAUTHENTICATED ||
                        _authState.value == AuthState.LOADING_ROLE
                    ) {
                    fetchUserRole()
                }
            }
            .launchIn(externalScope)

        externalScope.launch {
            fetchUserRoleBasedOnInitialToken()
        }
    }

    private suspend fun fetchUserRoleBasedOnInitialToken() {
        val accessToken = tokenRepository.accessToken.firstOrNull()
        if (accessToken.isNullOrEmpty()) {
            _authState.value = AuthState.UNAUTHENTICATED
        } else {
            fetchUserRole()
        }
    }

    private suspend fun fetchUserRole() {
        _authState.value = AuthState.LOADING_ROLE
        try {
            val response = authService.role()
            if (response.isSuccessful) {
                val roleResponse = response.body()
                when (roleResponse?.role?.lowercase()) {
                    "student" -> _authState.value = AuthState.AUTHENTICATED_STUDENT
                    "teacher" -> _authState.value = AuthState.AUTHENTICATED_TEACHER
                    else -> {
                        tokenRepository.clearTokens() // This will trigger the observer in init
                        // _authState.value = AuthState.UNAUTHENTICATED; // Not strictly needed due to token observer
                    }
                }
            }
            else {
                tokenRepository.clearTokens()
            }
        } catch (e: Exception) {
            Log.e("Error", " ", e)
            tokenRepository.clearTokens()
        }
    }
    override suspend fun logout() {
        tokenRepository.clearTokens()
    }
}