package com.kttq.attendassist.core.data.repositories.user

import com.kttq.attendassist.core.data.network.responses.UserOut

interface UserRepository {
    /**
     * Fetches the current user's details.
     * @return UserOut object if successful, null otherwise.
     */
    suspend fun getCurrentUser(): UserOut?
}

