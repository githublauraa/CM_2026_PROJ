package com.example.voxel_review.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.StorageRepository
import com.example.voxel_review.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository,
	private val storageRepository: StorageRepository,
	private val userRepository: UserRepository,
	private val reviewRepository: ReviewRepository,
) : ViewModel() {

	private val _uiState = MutableStateFlow(
		ProfileState(
			email = authRepository.currentUser?.email ?: "",
			profileImageUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""
		)
	)

	val uiState: StateFlow<ProfileState> = _uiState

	fun getProfileById(userId: String) {
		viewModelScope.launch { 
			val result = userRepository.getUserById(userId)
			if (result.isSuccess){
				val userProfileInfo = result.getOrNull()!!
				_uiState.value = _uiState.value.copy(
					profile = userProfileInfo
				)
			}
		}
	}

	fun getUserReviews(userId: String) {
		viewModelScope.launch {
			val result = reviewRepository.getUserReviews(userId)
			if (result.isSuccess){
				val userReviews = result.getOrNull()!!
				_uiState.value = _uiState.value.copy(
					reviews = userReviews
				)
			}
		}
	}
	// fun getAllProfiles() {
	// 	_uiState.update {
	// 		it.copy(profiles = LocalProfileProvider.profiles)
	// 	}
	// }

	fun logOut() {
		authRepository.logOut()
	}

	fun uploadImageFireBase(uri: Uri) {
		viewModelScope.launch {

			val result = storageRepository.uploadProfileImage(uri)

			if (result.isSuccess) {
				_uiState.update {
					it.copy(
						profileImageUrl = result.getOrNull()
					)
				}
			}
		}
	}
}
