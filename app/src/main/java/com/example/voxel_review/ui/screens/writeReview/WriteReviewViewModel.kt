package com.example.voxel_review.ui.screens.writeReview

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import com.example.voxel_review.data.dtos.CreateReviewDto
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.voxel_review.data.repository.ReviewRepository
import retrofit2.HttpException
import com.example.voxel_review.data.review.ReviewInfo
@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(WriteReviewState())
    val uiState: StateFlow<WriteReviewState> = _uiState.asStateFlow()
    fun loadGame(gameId: String) {
        val game = LocalGameProvider.games.firstOrNull { it.id == gameId }
            ?: LocalGameProvider.games.first()

        _uiState.update { it.copy(game = game) }
    }

    fun loadReview(review: ReviewInfo) {
        _uiState.update {
            it.copy(
                reviewText = review.descripcion,
                gameplayRating = review.ratingJugabilidad.toInt(),
                graphicsRating = review.ratingGraficos.toInt(),
                storyRating = review.ratingHistoria.toInt(),
                isPublished = false,
                errorMessage = null
            )
        }
    }

    fun updateReviewText(text: String) {
        _uiState.update { it.copy(reviewText = text, errorMessage = null) }
    }

    fun updateGameplayRating(rating: Int) {
        _uiState.update {
            it.copy(gameplayRating = rating)
        }
    }

    fun updateGraphicsRating(rating: Int) {
        _uiState.update {
            it.copy(graphicsRating = rating)
        }
    }

    fun updateStoryRating(rating: Int) {
        _uiState.update {
            it.copy(storyRating = rating)
        }
    }


    fun publishReview(userId: String, videoGameId: String) {
        viewModelScope.launch {
            val state = _uiState.value

            if (state.gameplayRating !in 0..5 ||
                state.graphicsRating !in 0..5 ||
                state.storyRating !in 0..5
            ) {

                _uiState.update {
                    it.copy(errorMessage = "Las calificaciones deben estar entre 0 y 5")
                }

            } else if (state.reviewText.isBlank()) {

                _uiState.update {
                    it.copy(errorMessage = "La descripción no puede estar vacía")
                }

            } else {
                val review = CreateReviewDto(
                    userId = userId.toInt(),
                    videoGameId = videoGameId.toInt(),
                    gameplayRating = state.gameplayRating,
                    graphicsRating = state.graphicsRating,
                    storyRating = state.storyRating,
                    content = state.reviewText
                )

                val result = reviewRepository.createReview(
                    userId,
                    videoGameId,
                    review
                )

                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            isPublished = true,
                            errorMessage = null
                        )
                    }
                } else {
                    val error = result.exceptionOrNull()

                    _uiState.update {
                        it.copy(
                            errorMessage = if (error is HttpException && error.code() == 400) {
                                "No se pudo publicar la reseña. Es posible que ya tengas una reseña de este videojuego."
                            } else {
                                "Ocurrió un error al publicar la reseña"
                            }
                        )
                    }
                }
            }
        }
    }

    fun updateReview(
        reviewId: String,
        userId: String,
        videoGameId: String
    ) {
        viewModelScope.launch {

            val state = _uiState.value

            if (state.gameplayRating !in 0..5 ||
                state.graphicsRating !in 0..5 ||
                state.storyRating !in 0..5
            ) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Las calificaciones deben estar entre 0 y 5"
                    )
                }

            } else if (state.reviewText.isBlank()) {

                _uiState.update {
                    it.copy(
                        errorMessage = "La descripción no puede estar vacía"
                    )
                }

            } else {

                val review = CreateReviewDto(
                    userId = userId.toInt(),
                    videoGameId = videoGameId.toInt(),
                    gameplayRating = state.gameplayRating,
                    graphicsRating = state.graphicsRating,
                    storyRating = state.storyRating,
                    content = state.reviewText
                )

                val result = reviewRepository.updateReview(
                    reviewId,
                    review
                )

                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            isPublished = true,
                            errorMessage = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            errorMessage = "Error al actualizar la reseña"
                        )
                    }
                }
            }
        }
    }
}
