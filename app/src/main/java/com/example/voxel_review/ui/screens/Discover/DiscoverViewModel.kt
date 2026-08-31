package com.example.voxel_review.ui.screens.Discover

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
class DiscoverViewModel: ViewModel() {
    var searchQuery by mutableStateOf("")
        private set

    fun updateSearchQuery(input: String) {
        searchQuery = input
    }

    var selectedGenre by mutableStateOf(LocalGenreProvider.generos.first())
        private set

    fun updateSelectedGenre(genre: GenreInfo) {
        selectedGenre = genre
    }
}