package com.example.voxel_review.ui.screens.profile

import com.example.voxel_review.data.profile.ProfileInfo


data class ProfileState(
    val profiles: List<ProfileInfo> = emptyList(),
    val profile: ProfileInfo? = null,
    val email: String ="",
    val profileImageUrl: String ? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
