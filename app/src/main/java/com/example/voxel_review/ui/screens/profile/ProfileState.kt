package com.example.voxel_review.ui.screens.profile

import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.data.profile.Profile

data class ProfileState(
	val profile: Profile = LocalProfileProvider.profiles.first()
)
