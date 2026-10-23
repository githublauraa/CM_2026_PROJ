package com.example.voxel_review.ui.screens.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    val uiState: StateFlow<ReviewDetailState> = _uiState.asStateFlow()

    private var reviewJob: Job? = null

    fun getGameReviews(gameId: String, reviewId: String = "") {
        reviewJob?.cancel()
        reviewJob = viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            reviewRepository.getSelectedReviewLive(gameId, reviewId)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            errorMessage = e.message,
                            isLoading = false
                        )
                    }
                }
                .collect { reviews ->
                    val selectedReview = if (reviewId.isBlank()) {
                        reviews.firstOrNull()
                    } else {
                        reviews.find { it.idResenia == reviewId }
                    }

                    _uiState.update {
                        it.copy(
                            review = selectedReview,
                            isLoading = false,
                            errorMessage = if (selectedReview == null) {
                                "Reseña no encontrada"
                            } else {
                                null
                            }
                        )
                    }
                }
        }
    }

    fun getUserReview(userId: String, reviewId: String) {
        // El ID identifica el documento: no hace falta descargar todas las reseñas del autor.
        getGameReviews(gameId = "", reviewId = reviewId)
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

            if (result.isFailure) {
                _uiState.update {
                    it.copy(errorMessage = result.exceptionOrNull()?.message
                        ?: "No se pudo actualizar el like")
                }
            }
        }
    }
}
