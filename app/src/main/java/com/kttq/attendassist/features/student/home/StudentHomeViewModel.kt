package com.kttq.attendassist.features.student.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kttq.attendassist.core.data.repositories.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StudentHomeUiState(
    val userName: String = "User", // Default name
    val isLoading: Boolean = false
    // TODO: Add state for incoming classes list
    // TODO: Add state for today's classes list
)

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentHomeUiState(isLoading = true))
    val uiState: StateFlow<StudentHomeUiState> = _uiState.asStateFlow()

    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = userRepository.getCurrentUser()
            _uiState.update {
                it.copy(
                    userName = user?.firstName ?: user?.email ?: "User",
                    isLoading = false
                )
            }
        }
    }

    fun onIncomingClassSeeAllClicked() {
        // TODO: Implement navigation to the full list of incoming classes
        // TODO: Or fetch more incoming classes data if displaying a preview
        println("Navigate to all incoming classes screen or fetch data.")
    }

    fun onClassTodaySeeAllClicked() {
        // TODO: Implement navigation to the full list of today's classes
        // TODO: Or fetch more today's classes data if displaying a preview
        println("Navigate to all today's classes screen or fetch data.")
    }

    // TODO: Add function to fetch incoming classes
    // fun fetchIncomingClasses() { }

    // TODO: Add function to fetch today's classes
    // fun fetchTodaysClasses() { }
}
