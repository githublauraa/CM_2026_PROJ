package com.example.voxel_review.ui.review

import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.data.repository.ReviewRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

internal enum class ReviewReaction {
    LIKE, DISLIKE, NONE;

    fun applyTo(review: ReviewInfo): ReviewInfo {
        val liked = this == LIKE
        val disliked = this == DISLIKE
        return review.copy(
            likedByCurrentUser = liked,
            dislikedByCurrentUser = disliked,
            cantidadLikes = (review.cantidadLikes + liked.compareTo(review.likedByCurrentUser))
                .coerceAtLeast(0),
            cantidadDislikes = (review.cantidadDislikes + disliked.compareTo(review.dislikedByCurrentUser))
                .coerceAtLeast(0)
        )
    }
}

private val ReviewInfo.reaction: ReviewReaction
    get() = when {
        likedByCurrentUser -> ReviewReaction.LIKE
        dislikedByCurrentUser -> ReviewReaction.DISLIKE
        else -> ReviewReaction.NONE
    }

internal class OptimisticReviewReactions(
    private val scope: CoroutineScope,
    private val repository: ReviewRepository
) {
    private val _pending = MutableStateFlow<Map<String, ReviewReaction>>(emptyMap())
    val pending = _pending.asStateFlow()

    fun toggle(
        review: ReviewInfo,
        userId: String,
        dislike: Boolean,
        liveReview: Flow<ReviewInfo?>,
        onError: (String?) -> Unit
    ) {
        val reviewId = review.idResenia
        if (userId.isBlank() || reviewId in _pending.value) return

        val selected = if (dislike) ReviewReaction.DISLIKE else ReviewReaction.LIKE
        val target = if (review.reaction == selected) ReviewReaction.NONE else selected
        _pending.update { it + (reviewId to target) }
        onError(null)

        scope.launch {
            try {
                val result = repository.toggleReviewReaction(reviewId, userId, dislike)
                if (result.isSuccess) {
                    // Mantén la reacción visible mientras llega el snapshot de la escritura.
                    withTimeoutOrNull(5_000) {
                        liveReview.first { it == null || it.reaction == target }
                    }
                } else {
                    onError(result.exceptionOrNull()?.message ?: "No se pudo guardar la reacción.")
                }
            } finally {
                _pending.update { it - reviewId }
            }
        }
    }
}
