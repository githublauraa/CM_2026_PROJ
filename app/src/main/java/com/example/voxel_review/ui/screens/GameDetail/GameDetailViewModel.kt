package com.example.voxel_review.ui.screens.GameDetail

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.data.InfoGame.GameDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameDetailViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameDetailState())
    val uiState: StateFlow<GameDetailState> = _uiState.asStateFlow()

    fun loadGame(game: GameDetailInfo, recommendedGames: List<GameDetailInfo>) {
        _uiState.update {
            it.copy(
                game = game,
                recommendedGames = recommendedGames
            )
        }
    }
}