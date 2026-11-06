package com.example.voxel_review.ui.screens.following

import com.example.voxel_review.data.info.profile.ProfileInfo

data class FollowingState(
    val users: List<ProfileInfo> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)
