
package com.example.voxel_review.ui.screens.GameDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.dtos.toGameDetailInfo
import com.example.voxel_review.data.repository.VideoGameRepository
import com.example.voxel_review.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class GameDetailViewModel @Inject constructor(
    private val videoGameRepository: VideoGameRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailState())
    val uiState: StateFlow<GameDetailState> = _uiState.asStateFlow()

    fun loadGame(gameId: String) {
        val id = gameId.toIntOrNull()

        if (id == null) {
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
                    error = null
                )
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

            reviewRepository.getGameReviews(gameId)
                .onSuccess { reviews ->
                    _uiState.update {
                        it.copy(reviews = reviews)
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            error = exception.message
                                ?: "No se pudieron cargar las reseñas"
                        )
                    }
                }

            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }
}
