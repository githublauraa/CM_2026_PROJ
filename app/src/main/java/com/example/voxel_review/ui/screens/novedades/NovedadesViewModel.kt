
package com.example.voxel_review.ui.screens.novedades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.VideoGameRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.dtos.toJuegoInfo
import com.example.voxel_review.data.repository.AuthRepository
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
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
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
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            videoGameRepository.getAllVideoGames()
                .onSuccess { games ->

                    val juegos = games.mapNotNull { game ->

                        val reviewsResult = reviewRepository.getGameReviews(
                            game.videoGameId.toString()
                        )

                        val reviews = reviewsResult.getOrNull()?: emptyList()



                        val promedio = if (reviews.isNotEmpty()) {
                            reviews.map { review ->
                                review.ratingGeneral
                            }.average().toFloat()
                        } else {
                            null
                        }

                        if (reviews.isNullOrEmpty()) {
                            return@mapNotNull null
                        }

                        game.toJuegoInfo().copy(
                            calificacion = promedio
                        )
                    }

                    _uiState.update {
                        it.copy(
                            listaJuegos = juegos,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->

                    _uiState.update {
                        it.copy(
                            listaJuegos = emptyList(),
                            isLoading = false,
                            error = exception.message
                                ?: "No se pudieron cargar los videojuegos"
                        )
                    }
                }
        }
    }

    fun toggleFollowedFilter(enabled: Boolean) {
        _uiState.update {
            it.copy(showOnlyFollowed = enabled)
        }

        if (enabled && _uiState.value.followedReviews.isEmpty()) {
            loadFollowedReviews()
        }
    }

    private fun loadFollowedReviews() {
        val currentUserId = authRepository.currentUser?.uid ?: ""

        if (currentUserId.isBlank()) {
            _uiState.update {
                it.copy(error = "Debes iniciar sesión para ver seguidos")
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            reviewRepository.getFollowedUsersReviews(currentUserId)
                .onSuccess { reviews ->
                    _uiState.update {
                        it.copy(
                            followedReviews = reviews,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "No se pudieron obtener las reseñas"
                        )
                    }
                }
        }
    }
}
