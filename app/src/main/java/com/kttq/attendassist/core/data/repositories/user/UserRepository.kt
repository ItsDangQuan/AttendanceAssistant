package com.kttq.attendassist.core.data.repositories.user

import com.kttq.attendassist.core.data.network.dtos.UserOut
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    /**
     * Fetches the current user's details.
     * @return UserOut object if successful, null otherwise.
     */
    suspend fun getCurrentUser(): UserOut?
}

// TODO: Refactor to the interface below. Remember to change the interface name.
interface UserRepositoryRefactor {
    /**
     * Observes the current user's data from an in-memory cache.
     * The cache is updated by calling [refreshCurrentUser].
     * Emits null if no user data is available in the cache or after a failed refresh.
     */
    fun observeCurrentUser(): Flow<UserOut?>

    /**
     * Fetches the latest user data from the API and updates the in-memory cache.
     * This method should handle its own errors internally or throw exceptions
     * to be caught by the caller (ViewModel).
     */
    suspend fun refreshCurrentUser()
}