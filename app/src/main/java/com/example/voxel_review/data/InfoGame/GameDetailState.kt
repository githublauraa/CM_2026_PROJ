package com.example.voxel_review.data.InfoGame

data class GameDetailState(
    val game: GameDetailInfo? = null,
    val recommendedGames: List<GameDetailInfo> = emptyList(),
)
