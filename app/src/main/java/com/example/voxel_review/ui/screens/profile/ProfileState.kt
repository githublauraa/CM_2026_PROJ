package com.example.voxel_review.ui.screens.profile

import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.data.profile.Profile
import sun.invoke.empty.Empty

data class ProfileState(
	val profiles: List<Profile> = emptyList()
)
