package com.example.voxel_review.ui.screens.editProfile

data class EditProfileState(
    val bio: String = "",
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val errorMessage: String? = null
)