package com.example.voxel_review.ui.screens.profile

import androidx.compose.ui.platform.LocalProvidableLocaleList
import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.ui.theme.provider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel: ViewModel() {

	private val _uiState = MutableStateFlow(ProfileState())
	val uiState: StateFlow<ProfileState> = _uiState

	fun getAllProfiles() {
		_uiState.update { it.copy(profiles = LocalProfileProvider.profiles) }
	}

	init {
		getAllProfiles()
	}

}