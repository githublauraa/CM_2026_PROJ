
package com.example.voxel_review.ui.screens.novedades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.VideoGameRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.dtos.toJuegoInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class NovedadesViewModel @Inject constructor(
    private val videoGameRepository: VideoGameRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NovedadesState())
    val uiState: StateFlow<NovedadesState> = _uiState.asStateFlow()

    init {
        getAllJuegos()
    }

    fun updateSelectedCategory(category: String) {
        _uiState.update {
            it.copy(
                categoriaSeleccionada = category
            )
        }
    }

    fun getAllJuegos() {
        if (_uiState.value.isLoading) return
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            videoGameRepository.getAllVideoGames()
                .onSuccess { games ->
                    reviewRepository.getAllReviews()
                        .onSuccess { reviews ->
                            val juegos = games.mapNotNull { game ->
                                val gameReviews = reviews.filter {
                                    it.videoGameId == game.videoGameId
                                }
                                if (gameReviews.isEmpty()) return@mapNotNull null
                                val promedio = gameReviews.map { it.ratingGeneral }
                                    .average().toFloat()
                                game.toJuegoInfo().copy(calificacion = promedio)
                            }
                            _uiState.update {
                                it.copy(listaJuegos = juegos, isLoading = false, error = null)
                            }
                        }
                        .onFailure { error ->
                            _uiState.update {
                                it.copy(isLoading = false, error = error.message
                                    ?: "No se pudieron cargar las reseñas")
                            }
                        }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message
                            ?: "No se pudieron cargar los videojuegos")
                    }
                }
        }
    }
}
