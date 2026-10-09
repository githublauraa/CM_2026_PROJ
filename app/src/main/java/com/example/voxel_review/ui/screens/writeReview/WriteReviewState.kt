package com.example.voxel_review.ui.screens.writeReview

import com.example.voxel_review.data.InfoGame.GameDetailInfo

data class WriteReviewState(
    val game: GameDetailInfo? = null,
    val reviewText: String = "",
    val gameplayRating: Int = 0,
    val graphicsRating: Int = 0,
    val storyRating: Int = 0,
    val isPublished: Boolean = false,
    val errorMessage: String? = null
)
