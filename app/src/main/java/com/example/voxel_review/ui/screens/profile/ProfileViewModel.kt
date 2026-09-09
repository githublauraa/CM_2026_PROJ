package com.example.voxel_review.ui.screens.profile

import androidx.compose.ui.platform.LocalProvidableLocaleList
import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.ui.theme.provider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(ProfileState(
		email = authRepository.currentUser?.email?: ""
	))
	val uiState: StateFlow<ProfileState> = _uiState

	fun getProfileById(id: Int) {
		val profile = LocalProfileProvider.profiles.find {
			it.id == id
		}
		_uiState.update {
			it.copy(profile = profile)
		}
	}

	fun getAllProfiles() {
		_uiState.update { it.copy(profiles = LocalProfileProvider.profiles) }
	}

	fun logOut(){
		authRepository.logOut()
	}



}