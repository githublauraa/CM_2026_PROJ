package com.example.voxel_review.ui.screens.GameDetail

import com.example.voxel_review.data.InfoGame.GameDetailInfo

data class GameDetailState(

    val game: GameDetailInfo? = null,
    val recommendedGames: List<GameDetailInfo> = emptyList()
)

