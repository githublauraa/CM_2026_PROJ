package com.example.voxel_review.ui.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel: ViewModel() {
	 
	private val _uiState = MutableStateFlow(ProfileState())
	val uiState: StateFlow<ProfileState> = _uiState
	

}
