package com.example.voxel_review.ui.screens.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.UserRepository
import com.example.voxel_review.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    reviewRepository: ReviewRepository,
    authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsState())
    val uiState = _uiState.asStateFlow()

    init {
        val userId = authRepository.currentUser?.uid
        if (userId.isNullOrBlank()) {
            _uiState.value = NotificationsState(isLoading = false, hasError = true, likesLoading = false, likesError = true)
        } else {
            viewModelScope.launch {
                val result = userRepository.getFollowers(userId)
                val followers = result.getOrDefault(emptyList()).map { follower ->
                    NotificationInfo(
                        id = follower.id,
                        title = follower.nick,
                        message = "Empezó a seguirte",
                        imageUrl = follower.pfp
                    )
                }
                _uiState.update {
                    it.copy(followers = followers, isLoading = false, hasError = result.isFailure)
                }
            }
            viewModelScope.launch {
                val result = reviewRepository.getReceivedLikes(userId)
                _uiState.update {
                    it.copy(
                        likes = result.getOrDefault(emptyList()),
                        likesLoading = false,
                        likesError = result.isFailure
                    )
                }
            }
        }
    }
}
