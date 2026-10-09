package com.example.voxel_review.ui.screens.profile

import com.example.voxel_review.data.profile.ProfileInfo
import com.example.voxel_review.data.review.ReviewInfo


data class ProfileState(
    val profiles: List<ProfileInfo> = emptyList(),
    val profile: ProfileInfo? = null,
    val reviews: List<ReviewInfo> = emptyList(),
    val email: String ="",
    val profileImageUrl: String ? = null,
    val isReviewDeleted: Boolean = false,
    val errorMessage: String? = null
)
