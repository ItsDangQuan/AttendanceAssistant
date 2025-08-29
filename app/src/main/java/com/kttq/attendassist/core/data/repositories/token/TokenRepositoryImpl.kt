package com.kttq.attendassist.core.data.repositories.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.kttq.attendassist.core.data.preferences.auth.AuthPreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : TokenRepository {
    override val accessToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AuthPreferencesKeys.ACCESS_TOKEN]
    }
    override val refreshToken: Flow<String?> = dataStore.data.map { preferences ->
        preferences[AuthPreferencesKeys.REFRESH_TOKEN]
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        dataStore.edit { preferences ->
            preferences[AuthPreferencesKeys.ACCESS_TOKEN] = accessToken
            preferences[AuthPreferencesKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clearTokens() {
        dataStore.edit { preferences ->
            preferences.remove(AuthPreferencesKeys.ACCESS_TOKEN)
            preferences.remove(AuthPreferencesKeys.REFRESH_TOKEN)
        }
    }

}