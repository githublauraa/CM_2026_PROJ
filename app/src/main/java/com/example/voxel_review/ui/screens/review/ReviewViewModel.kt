package com.example.voxel_review.ui.screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState.asStateFlow()

    fun getGameReviews(gameId: String, reviewId: String = "") {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = reviewRepository.getGameReviews(gameId)

            if (result.isSuccess) {
                val reviews = result.getOrNull().orEmpty()
                val review = if (reviewId.isBlank()) {
                    reviews.firstOrNull()
                } else {
                    reviews.find { it.idResenia == reviewId }
                }

                _uiState.update {
                    it.copy(
                        review = review,
                        isLoading = false,
                        errorMessage = if (review == null) {
                            "Reseña no encontrada"
                        } else {
                            null
                        }
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getUserReview(userId: String, reviewId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = reviewRepository.getUserReviews(userId)

            if (result.isSuccess) {
                val review = result.getOrNull()?.find {
                    it.idResenia == reviewId
                }

                _uiState.update {
                    it.copy(
                        review = review,
                        isLoading = false,
                        errorMessage = if (review == null) {
                            "Reseña no encontrada"
                        } else {
                            null
                        }
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getReviewComments(reviewId: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = reviewRepository.getReviewComments(reviewId)

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        comments = result.getOrNull() ?: emptyList(),
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        errorMessage = result.exceptionOrNull()?.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun sendOrDeleteReviewLike(reviewId: String, userId: String){
        viewModelScope.launch {
            val result = reviewRepository.sendOrDeleteReviewLike(reviewId, userId)

            if (result.isSuccess){
                _uiState.update {
                    it.copy(
                        review = it.review?.copy(
                            likedByCurrentUser = !it.review.likedByCurrentUser,
                            cantidadLikes = if (it.review.likedByCurrentUser) it.review.cantidadLikes - 1 else it.review.cantidadLikes +1)
                        )
                }
            } else{

            }
        }
    }
}
