package com.example.voxel_review.ui.screens.following

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
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FollowingState())
    val uiState: StateFlow<FollowingState> = _uiState
    private var loadJob: Job? = null

    fun loadUsers(userId: String, showFollowers: Boolean) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { FollowingState() }
            if (userId.isBlank()) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo identificar al usuario")
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
            }.onFailure {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = if (showFollowers) "No se pudieron cargar los seguidores"
                            else "No se pudieron cargar las personas seguidas"
                    )
                }
            }
        }
    }
}
