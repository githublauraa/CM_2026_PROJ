
package com.example.voxel_review.ui.screens.Discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.info.InfoDiscover.GenreInfo
import com.example.voxel_review.data.info.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.dtos.toTrendingSearchInfo
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.VideoGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val videoGameRepository: VideoGameRepository,
    private val reviewRepository: ReviewRepository
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

    fun loadVideoGames() {
        if (_uiState.value.isLoading) return
        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            val reviews = async { reviewRepository.getAllReviews() }
            videoGameRepository.getAllVideoGames()
                .onSuccess { games ->

                    val reviewCounts = reviews.await().getOrNull().orEmpty()
                        .groupingBy { it.videoGameId }
                        .eachCount()
                    val trendingSearches = games.map { game ->
                        game.toTrendingSearchInfo().copy(
                            reviewCount = reviewCounts[game.videoGameId] ?: 0
                        )
                    }

                    _uiState.update {
                        it.copy(
                            trendingSearches = trendingSearches,
                            isLoading = false
                        )
                    }
                }
                .onFailure { exception ->
                    reviews.cancel()

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "No se pudieron cargar los juegos"
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
