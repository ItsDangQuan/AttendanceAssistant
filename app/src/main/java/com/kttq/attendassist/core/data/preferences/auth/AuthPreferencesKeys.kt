package com.kttq.attendassist.core.data.preferences.auth

import androidx.datastore.preferences.core.stringPreferencesKey

object AuthPreferencesKeys {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    val AUTH_STATE = stringPreferencesKey("auth_state")
}