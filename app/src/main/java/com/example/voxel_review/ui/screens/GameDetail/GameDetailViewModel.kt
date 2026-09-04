package com.example.voxel_review.ui.screens.GameDetail

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel que administra la carga de datos y el estado de la pantalla de detalles de un juego.
 */
class GameDetailViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameDetailState())
    val uiState: StateFlow<GameDetailState> = _uiState

    fun loadGame(gameIndex: Int) {
        val game = LocalGameProvider.games.getOrElse(gameIndex) {
            LocalGameProvider.games.first()
        }
        _uiState.update {
            it.copy(
                game = game,
                recommendedGames = LocalGameRecomendedProvider.recommendedGames
            )
        }
    }

    fun getAllGames() {
        _uiState.update {
            it.copy(allGames = LocalGameProvider.games)
        }
    }
}
