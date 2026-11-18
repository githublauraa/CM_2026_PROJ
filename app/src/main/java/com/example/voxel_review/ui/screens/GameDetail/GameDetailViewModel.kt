
package com.example.voxel_review.ui.screens.GameDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.dtos.toGameDetailInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.LibraryRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.VideoGameRepository
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


@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val videoGameRepository: VideoGameRepository,
    private val reviewRepository: ReviewRepository,
    private val libraryRepository: LibraryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailState())
    private val reactions = OptimisticReviewReactions(viewModelScope, reviewRepository)

    val uiState: StateFlow<GameDetailState> = combine(_uiState, reactions.pending) { state, pendingReactions ->
        val displayedReviews = state.reviews.map { review ->
            val pendingReaction = pendingReactions[review.idResenia]
            if (pendingReaction == null) {
                review
            } else {
                pendingReaction.applyTo(review)
            }
        }

        state.copy(
            reviews = displayedReviews,
            updatingReactionIds = pendingReactions.keys
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value
    )

    init {
        val userId = authRepository.currentUser?.uid ?: ""

        _uiState.update {
            it.copy(currentUserId = userId)
        }
    }

    fun refreshEmailVerification() {
        val user = authRepository.currentUser
        _uiState.update {
            it.copy(
                currentUserId = user?.uid,
                isEmailVerified = user?.isEmailVerified == true,
                verificationError = null
            )
        }
        if (user == null) return

        viewModelScope.launch {
            authRepository.reloadAndCheckEmailVerified()
                .onSuccess { verified ->
                    if (authRepository.currentUser?.uid == user.uid) {
                        _uiState.update { it.copy(isEmailVerified = verified, verificationError = null) }
                    }
                }
                .onFailure { error ->
                    if (authRepository.currentUser?.uid == user.uid) {
                        _uiState.update { it.copy(verificationError = error.message) }
                    }
                }
        }
    }

    fun loadGame(gameId: String) {
        val id = gameId

        if (gameId.isBlank()) {
            _uiState.update {
                it.copy(error = "ID del videojuego inválido")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    game = null,
                    reviews = emptyList(),
                    isLoading = true,
                    error = null,
                    reviewsError = null,
                    libraryStatus = null,
                    libraryError = null
                )
            }

            val uid = authRepository.currentUser?.uid
            if (!uid.isNullOrBlank()) {
                launch {
                    libraryRepository.getGameStatus(uid, gameId)
                        .onSuccess { status ->
                            _uiState.update { it.copy(libraryStatus = status) }
                        }
                        .onFailure { failure ->
                            _uiState.update { it.copy(libraryError = failure.message) }
                        }
                }
            }

            videoGameRepository.getVideoGameById(id)
                .onSuccess { videoGame ->
                    _uiState.update {
                        it.copy(
                            game = videoGame.toGameDetailInfo()
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message
                                ?: "No se pudo cargar el videojuego"
                        )
                    }
                }

            reviewRepository.getReviewsLive(gameId)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            reviewsError = e.message,
                            isLoading = false
                        )
                    }
                }
                .collect { reviews ->
                    _uiState.update {
                        it.copy(
                            reviews = reviews,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun toggleLibraryStatus(status: LibraryStatus) {
        val currentState = _uiState.value
        val uid = authRepository.currentUser?.uid
        val game = currentState.game

        if (uid.isNullOrBlank() || game == null || currentState.isUpdatingLibrary) return

        val previousStatus = currentState.libraryStatus
        val isRemoving = previousStatus == status

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isUpdatingLibrary = true,
                    libraryStatus = if (isRemoving) null else status,
                    libraryError = null
                )
            }

            val result = if (isRemoving) {
                libraryRepository.removeGameFromLibrary(uid, game.id)
            } else {
                libraryRepository.setGameStatus(uid, game, status)
            }

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(isUpdatingLibrary = false, libraryError = null)
                }
            } else {
                _uiState.update {
                    it.copy(
                        isUpdatingLibrary = false,
                        libraryStatus = previousStatus,
                        libraryError = result.exceptionOrNull()?.message
                            ?: "No se pudo actualizar tu colección"
                    )
                }
            }
        }
    }

    fun toggleReviewReaction(reviewId: String, userId: String, dislike: Boolean) {
        val review = _uiState.value.reviews.find { it.idResenia == reviewId } ?: return
        reactions.toggle(
            review = review,
            userId = userId,
            dislike = dislike,
            liveReview = _uiState.map { it.reviews.find { review -> review.idResenia == reviewId } }
        ) { message ->
            _uiState.update { it.copy(reactionError = message) }
        }
    }
}
