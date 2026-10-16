package com.example.voxel_review.ui.screens.editProfile

data class EditProfileState(
    val bio: String = "",
    val isProfileLoading: Boolean = false,
    val isProfileLoaded: Boolean = false,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)
