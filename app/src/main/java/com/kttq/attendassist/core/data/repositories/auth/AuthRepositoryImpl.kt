package com.kttq.attendassist.core.data.repositories.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.kttq.attendassist.core.data.network.AuthService
import com.kttq.attendassist.core.data.network.dtos.UserLogin
import com.kttq.attendassist.core.data.preferences.auth.AuthPreferencesKeys
import com.kttq.attendassist.core.data.repositories.token.TokenRepository
import com.kttq.attendassist.core.domain.model.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor(
    @param:Named("AuthAuthService") private val authService: AuthService,
    private val tokenRepository: TokenRepository,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {

//    override fun getAuthState(): Flow<AuthState> =
//        tokenRepository.accessToken
//            .map { token ->        // mapLatest allows suspend
//                if (token.isNullOrEmpty()) {
//                    AuthState.UNAUTHENTICATED
//                } else {
//                    try {
//                        val response = authService.role() // suspend call
//                        if (response.isSuccessful) {
//                            when (response.body()?.role?.lowercase()) {
//                                "student" -> AuthState.AUTHENTICATED_STUDENT
//                                "teacher" -> AuthState.AUTHENTICATED_TEACHER
//                                else -> {
//                                    tokenRepository.clearTokens()
//                                    AuthState.UNAUTHENTICATED
//                                }
//                            }
//                        } else {
//                            tokenRepository.clearTokens()
//                            AuthState.UNAUTHENTICATED
//                        }
//                    } catch (e: Exception) {
//                        tokenRepository.clearTokens()
//                        AuthState.UNAUTHENTICATED
//                    }
//                }
//            }

    override val authState: Flow<AuthState?> = dataStore.data.map { preferences ->
        when (preferences[AuthPreferencesKeys.AUTH_STATE]) {
            "UNAUTHENTICATED" -> AuthState.UNAUTHENTICATED
            "AUTHENTICATED_STUDENT" -> AuthState.AUTHENTICATED_STUDENT
            "AUTHENTICATED_TEACHER" -> AuthState.AUTHENTICATED_TEACHER
            else -> null
        }
    }

    override suspend fun login(userLogin: UserLogin): Boolean {
        return try {
            val response = authService.login(userLogin)
            if (response.isSuccessful && response.body() != null) {
                val token = response.body()!!
                tokenRepository.saveTokens(
                    token.accessToken,
                    token.refreshToken
                )
                sync()
                true
            } else {
                tokenRepository.clearTokens()
                sync()
                false
            }
        } catch (e: Exception) {
            tokenRepository.clearTokens()
            sync()
            false
        }
    }

    override suspend fun logout() {
        tokenRepository.clearTokens()
        sync()
    }

    override suspend fun sync() {
        try {
            val response = authService.role() // suspend call
            if (response.isSuccessful) {
                when (response.body()?.role?.lowercase()) {
                    "student" -> dataStore.edit {
                        it[AuthPreferencesKeys.AUTH_STATE] = "AUTHENTICATED_STUDENT"
                    }

                    "teacher" -> dataStore.edit {
                        it[AuthPreferencesKeys.AUTH_STATE] = "AUTHENTICATED_TEACHER"
                    }

                    else -> {
                        tokenRepository.clearTokens()
                        dataStore.edit {
                            it[AuthPreferencesKeys.AUTH_STATE] = "UNAUTHENTICATED"
                        }
                    }
                }
            } else {
                tokenRepository.clearTokens()
                dataStore.edit {
                    it[AuthPreferencesKeys.AUTH_STATE] = "UNAUTHENTICATED"
                }
            }
        } catch (e: Exception) {
            tokenRepository.clearTokens()
            dataStore.edit {
                it[AuthPreferencesKeys.AUTH_STATE] = "UNAUTHENTICATED"
            }
        }
    }
}