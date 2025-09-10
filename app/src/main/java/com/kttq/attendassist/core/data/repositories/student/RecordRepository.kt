package com.kttq.attendassist.core.data.repositories.student

import kotlinx.coroutines.flow.Flow

interface RecordRepository {
    /**
     * Observes all attendance records for the currently logged-in student
     * from an in-memory cache.
     * The cache is updated by calling [refreshAllStudentRecords].
     * Emits an empty list if no records are available.
     */
    fun observeAllStudentRecords(): Flow<List<Record>>

    /**
     * Fetches the latest records from the API and updates the in-memory cache.
     * This method should handle its own errors internally or throw exceptions.
     */
    suspend fun refreshAllStudentRecords()
}