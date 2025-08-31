package com.kttq.attendassist.core.domain.model

enum class AuthState {
    LOADING_ROLE,
    UNAUTHENTICATED,
    AUTHENTICATED_STUDENT,
    AUTHENTICATED_TEACHER,
}