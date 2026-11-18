package com.example.voxel_review.ui.screens.following

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val strings: AppStrings
) : ViewModel() {
    private val _uiState = MutableStateFlow(FollowingState())
    val uiState: StateFlow<FollowingState> = _uiState
    private var loadJob: Job? = null

    fun loadUsers(userId: String, showFollowers: Boolean) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { FollowingState(showingFollowers = showFollowers) }
            if (userId.isBlank()) {
                _uiState.update {
                    it.copy(isLoading = false, error = strings[R.string.error_user_id])
                }
                return@launch
            }
            val result = if (showFollowers) {
                userRepository.getFollowers(userId)
            } else {
                userRepository.getFollowing(userId)
            }
            result.onSuccess { users ->
                _uiState.update { it.copy(users = users, isLoading = false, error = null) }
            }.onFailure { failure ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = failure.message ?: strings[R.string.error_load_list]
                    )
                }
            }
        }
    }
}
