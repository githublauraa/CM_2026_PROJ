package com.example.voxel_review.ui.screens.GameDetail

import android.R.attr.id
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import com.example.voxel_review.data.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import kotlinx.coroutines.launch

/**
 * ViewModel que administra la carga de datos y el estado de la pantalla de detalles de un juego.
 */
@HiltViewModel
class GameDetailViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailState())
    val uiState: StateFlow<GameDetailState> = _uiState

    fun loadGame(gameId: String) {
        val game = LocalGameProvider.games.firstOrNull { it.id == gameId }
            ?: LocalGameProvider.games.first()
        _uiState.update {
            it.copy(
                game = game,
                recommendedGames = LocalGameRecomendedProvider.recommendedGames
            )
        }
    }
}

