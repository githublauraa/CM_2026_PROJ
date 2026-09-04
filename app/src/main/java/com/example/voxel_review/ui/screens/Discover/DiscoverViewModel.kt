package com.example.voxel_review.ui.screens.Discover

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.InfoDiscover.LocalTrendingSearchProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel que gestiona el estado de la pantalla de Discover y las interacciones del usuario.
 */
class DiscoverViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DiscoverState())
    val uiState: StateFlow<DiscoverState> = _uiState

    init {
        loadDiscoverData()
    }

    private fun loadDiscoverData() {
        val genres = LocalGenreProvider.generos
        val trendingSearches = LocalTrendingSearchProvider.tendencias

        _uiState.update {
            it.copy(
                genres = genres,
                trendingSearches = trendingSearches,
                selectedGenre = genres.firstOrNull()
            )
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