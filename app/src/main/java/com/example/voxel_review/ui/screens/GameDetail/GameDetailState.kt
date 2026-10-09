package com.example.voxel_review.ui.screens.GameDetail

import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.data.review.ReviewInfo

data class GameDetailState(
    val game: GameDetailInfo? = null,
    val allGames: List<GameDetailInfo> = emptyList(),
    val recommendedGames: List<GameDetailInfo> = emptyList(),
    val reviews: List<ReviewInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)