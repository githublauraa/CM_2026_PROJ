package com.example.voxel_review.ui.screens.writeReview

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WriteReviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(WriteReviewState())
    val uiState: StateFlow<WriteReviewState> = _uiState.asStateFlow()

    fun loadGame(gameIndex: Int) {
        val game = LocalGameProvider.games.getOrElse(gameIndex) {
            LocalGameProvider.games.first()
        }
        _uiState.update { it.copy(game = game) }
    }

    fun updateReviewText(text: String) {
        _uiState.update { it.copy(reviewText = text) }
    }

    fun updateRating(rating: Int) {
        _uiState.update { it.copy(rating = rating) }
    }

    fun publishReview() {
        // TODO: Implementar lógica de publicación
    }
}
