package com.kttq.attendassist.core.data.repositories.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.kttq.attendassist.core.data.network.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val dataStore: DataStore<Preferences>
) : AuthRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun logout(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshToken(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

    override fun getRefreshToken(): String? {
        TODO("Not yet implemented")
    }
}