
package com.example.voxel_review.ui.screens.writeReview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.example.voxel_review.data.dtos.toGameDetailInfo
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.VideoGameRepository
import com.example.voxel_review.data.review.ReviewInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
    private val videoGameRepository: VideoGameRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WriteReviewState())
    val uiState: StateFlow<WriteReviewState> = _uiState.asStateFlow()

    fun currentUserId(): String? {
        return authRepository.currentUser?.uid
    }

    fun loadGame(gameId: String) {

        if (gameId.isBlank()) {
            _uiState.update {
                it.copy(
                    game = null,
                    errorMessage = "ID del videojuego inválido"
                )
            }
            return
        }

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    game = null,
                    errorMessage = null
                )
            }

            videoGameRepository.getVideoGameById(gameId)
                .onSuccess { videoGame ->
                    _uiState.update {
                        it.copy(
                            game = videoGame.toGameDetailInfo(),
                            errorMessage = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            errorMessage = exception.message
                                ?: "No se pudo cargar el videojuego"
                        )
                    }
                }
        }
    }

    fun loadReview(review: ReviewInfo) {
        _uiState.update {
            it.copy(
                reviewText = review.descripcion,
                gameplayRating = review.ratingJugabilidad.toInt(),
                graphicsRating = review.ratingGraficos.toInt(),
                storyRating = review.ratingHistoria.toInt(),
                editingReviewId = review.idResenia,
                isPublished = false,
                errorMessage = null
            )
        }
    }

    fun loadReviewForEdit(userId: String, reviewId: String) {
        viewModelScope.launch {

            Log.d("REVIEW_TEST", "Usuario: $userId")
            Log.d("REVIEW_TEST", "Reseña: $reviewId")

            val result = reviewRepository.getUserReviews(userId)

            Log.d(
                "REVIEW_TEST",
                "Reseñas encontradas: ${result.getOrNull()?.size}"
            )

            val review = result.getOrNull()
                ?.find { it.idResenia == reviewId }

            if (review != null) {

                Log.d(
                    "REVIEW_TEST",
                    "Reseña encontrada: ${review.descripcion}"
                )

                loadReview(review)

            } else {

                Log.d("REVIEW_TEST", "No se encontró la reseña")

                _uiState.update {
                    it.copy(
                        errorMessage = "No se pudo cargar la reseña para editar"
                    )
                }
            }
        }
    }

    fun loadExistingReview(gameId: String) {
        viewModelScope.launch {

            val userId = authRepository.currentUser?.uid

            if (userId == null) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Debes iniciar sesión"
                    )
                }
                return@launch
            }

            Log.d("REVIEW_TEST", "Usuario: $userId")
            Log.d("REVIEW_TEST", "Videojuego: $gameId")

            reviewRepository.getUserReviews(userId)
                .onSuccess { reviews ->

                    Log.d(
                        "REVIEW_TEST",
                        "Reseñas encontradas: ${reviews.size}"
                    )

                    reviews.forEach { review ->
                        Log.d(
                            "REVIEW_TEST",
                            "Review ID: ${review.idResenia}, Game ID: ${review.videoGameId}"
                        )
                    }

                    val existingReview = reviews.find {
                        it.videoGameId == gameId
                    }

                    if (existingReview != null) {

                        Log.d(
                            "REVIEW_TEST",
                            "Reseña encontrada: ${existingReview.descripcion}"
                        )

                        loadReview(existingReview)

                    } else {

                        Log.d(
                            "REVIEW_TEST",
                            "No existe reseña para este videojuego"
                        )

                        _uiState.update {
                            it.copy(
                                editingReviewId = null,
                                reviewText = "",
                                gameplayRating = 0,
                                graphicsRating = 0,
                                storyRating = 0,
                                isPublished = false
                            )
                        }
                    }
                }
                .onFailure { exception ->

                    Log.e(
                        "REVIEW_TEST",
                        "Error al consultar reseñas",
                        exception
                    )

                    _uiState.update {
                        it.copy(
                            errorMessage = exception.message
                                ?: "No se pudieron cargar las reseñas"
                        )
                    }
                }
        }
    }

    fun updateReviewText(text: String) {
        _uiState.update {
            it.copy(
                reviewText = text,
                errorMessage = null
            )
        }
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

    fun publishReview(videoGameId: String) {
        viewModelScope.launch {

            val userId = authRepository.currentUser?.uid
                ?: return@launch

            val state = _uiState.value

            if (
                state.gameplayRating !in 0..5 ||
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
                    userId = userId,
                    videoGameId = videoGameId,
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
                            errorMessage =
                                if (error is HttpException && error.code() == 400) {
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

            if (
                state.gameplayRating !in 0..5 ||
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
                    userId = userId,
                    videoGameId = videoGameId,
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
