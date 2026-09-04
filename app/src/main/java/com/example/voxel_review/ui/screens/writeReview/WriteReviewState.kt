package com.example.voxel_review.ui.screens.writeReview

import com.example.voxel_review.data.InfoGame.GameDetailInfo

data class WriteReviewState(
    val game: GameDetailInfo? = null,
    val reviewText: String = "",
    val rating: Int = 0
)
