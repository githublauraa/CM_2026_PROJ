package com.example.voxel_review.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState = _uiState.asStateFlow()

    init {
        val userId = authRepository.currentUser?.uid
        if (userId.isNullOrBlank()) {
            _uiState.value = NotificationsState(isLoading = false, hasError = true)
        } else {
            viewModelScope.launch {
                _uiState.value = userRepository.getFollowers(userId).fold(
                    onSuccess = { followers ->
                        NotificationsState(
                            followers = followers.map { follower ->
                                NotificationInfo(
                                    id = follower.id,
                                    title = follower.nick,
                                    message = "Empezó a seguirte",
                                    imageUrl = follower.pfp
                                )
                            },
                            isLoading = false
                        )
                    },
                    onFailure = { NotificationsState(isLoading = false, hasError = true) }
                )
            }
        }
    }
}
