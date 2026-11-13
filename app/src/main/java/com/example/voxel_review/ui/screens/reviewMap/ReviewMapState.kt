package com.example.voxel_review.ui.screens.reviewMap

import com.example.voxel_review.data.info.review.ReviewInfo

data class ReviewMapState(
    val reviews: List<ReviewInfo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
