package com.example.voxel_review.ui.screens.review

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.ui.review.OptimisticReviewReactions
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val strings: AppStrings
): ViewModel() {

    private val _uiState = MutableStateFlow(ReviewDetailState())
    private val reactions = OptimisticReviewReactions(viewModelScope, reviewRepository, strings)

    val uiState: StateFlow<ReviewDetailState> = combine(_uiState, reactions.pending) { state, pendingReactions ->
        val review = state.review
        val pendingReaction = review?.let { pendingReactions[it.idResenia] }

        val displayedReview = if (review != null && pendingReaction != null) {
            pendingReaction.applyTo(review)
        } else {
            review
        }

        state.copy(
            review = displayedReview,
            isUpdatingReaction = pendingReaction != null
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

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
                .collect { selectedReview ->
                    _uiState.update {
                        it.copy(
                            review = selectedReview,
                            isLoading = false,
                            errorMessage = if (selectedReview == null) {
                                strings[R.string.review_not_found]
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

    fun toggleReviewReaction(reviewId: String, userId: String, dislike: Boolean) {
        val review = _uiState.value.review?.takeIf { it.idResenia == reviewId } ?: return
        reactions.toggle(
            review = review,
            userId = userId,
            dislike = dislike,
            liveReview = _uiState.map { it.review?.takeIf { review -> review.idResenia == reviewId } }
        ) { message ->
            _uiState.update { it.copy(reactionError = message) }
        }
    }
}
