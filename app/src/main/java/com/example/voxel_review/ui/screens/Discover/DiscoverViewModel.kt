
package com.example.voxel_review.ui.screens.Discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.info.InfoDiscover.GenreInfo
import com.example.voxel_review.data.info.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.dtos.toTrendingSearchInfo
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.VideoGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val videoGameRepository: VideoGameRepository,
    private val reviewRepository: ReviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DiscoverState())
    val uiState: StateFlow<DiscoverState> = _uiState.asStateFlow()

    init {
        loadDiscoverData()
    }

    private fun loadDiscoverData() {
        val genres = LocalGenreProvider.generos

        _uiState.update {
            it.copy(
                genres = genres,
                selectedGenre = genres.firstOrNull()
            )
        }

        loadVideoGames()
    }

    private fun loadVideoGames() {
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            videoGameRepository.getAllVideoGames()
                .onSuccess { games ->

                    val trendingSearches = games.map {
                        it.toTrendingSearchInfo()
                    }

                    _uiState.update {
                        it.copy(
                            trendingSearches = trendingSearches,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message
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
    fun updateSearchQuery(input: String) {
        _uiState.update {
            it.copy(searchQuery = input)
        }
    }

    fun updateSelectedGenre(genre: GenreInfo) {
        _uiState.update {
            it.copy(selectedGenre = genre)
        }
    }
}
