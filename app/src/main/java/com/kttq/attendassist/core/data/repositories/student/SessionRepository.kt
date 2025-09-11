package com.kttq.attendassist.core.data.repositories.student

import com.kttq.attendassist.core.model.StudentSession // As defined in SessionConfirmViewModel or a shared location
import kotlinx.coroutines.flow.Flow

// TODO: Remove this after review (all usage have been removed)
/**
 * Repository interface for managing session details and attendance confirmation.
 */
interface SessionRepository {
    /**
     * Observes the details of a specific session from an in-memory cache.
     * The cache for this sessionId is updated by calling [refreshSessionDetails].
     *
     * @param sessionId The ID of the session to observe.
     * @return A Flow emitting the Session details, or null if not found/cached or on error during refresh.
     */
    fun observeSessionDetails(sessionId: String): Flow<StudentSession?>

    /**
     * Fetches the latest details for a specific session from the API
     * and updates the in-memory cache for that sessionId.
     * This method should handle its own errors (e.g., update the flow with null or a specific error state)
     * or throw an exception to be caught by the ViewModel.
     *
     * @param sessionId The ID of the session to refresh.
     */
    suspend fun refreshSessionDetails(sessionId: String)

    /**
     * Confirms attendance for the current student for a specific session.
     *
     * @param sessionId The ID of the session for which to confirm attendance.
     * @param studentId The ID of the student confirming attendance.
     * @return True if attendance was successfully confirmed, false otherwise.
     *         (Alternatively, this could return a more detailed Result object or throw exceptions).
     */

    // Using this endpoint: /api/v1/student/roll_call
    // suspend fun confirmAttendance(sessionId: String, studentId: String): Boolean
    suspend fun confirmAttendance(sessionId: String): Boolean

    // At first, we can use this:
    // /api/v1/session/ and then filter in the client.
    // From this, using /api/v1/class/{class_id}/information to get information about the class
    // But no way to get information about the course and teacher ?
    suspend fun getStudentSessionById(sessionId: String): StudentSession?
}

