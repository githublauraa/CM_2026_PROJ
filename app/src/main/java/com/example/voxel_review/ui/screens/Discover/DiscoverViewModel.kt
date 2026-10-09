
package com.example.voxel_review.ui.screens.Discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.dtos.toTrendingSearchInfo
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
    private val videoGameRepository: VideoGameRepository
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
