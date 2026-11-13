package com.example.voxel_review.ui.screens.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.LibraryRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.repository.StorageRepository
import com.example.voxel_review.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.voxel_review.data.dtos.CreateReviewDto
import com.google.firebase.auth.FirebaseAuth

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository,
	private val storageRepository: StorageRepository,
	private val userRepository: UserRepository,
	private val reviewRepository: ReviewRepository,
	private val libraryRepository: LibraryRepository,
) : ViewModel() {

	private val _uiState = MutableStateFlow(
		ProfileState(
			currentUserId = authRepository.currentUser?.uid ?: "",
			email = authRepository.currentUser?.email ?: "",
			profileImageUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""
		)
	)

	val uiState: StateFlow<ProfileState> = _uiState

	fun getProfileById(userId: String) {
        _uiState.update { it.copy(errorMessage = null, verificationMessage = null) }
		viewModelScope.launch { 
			val result = userRepository.getUserById(userId)
			if (result.isSuccess){
				var userProfileInfo = result.getOrNull()!!
				if (userId == authRepository.currentUser?.uid && !userProfileInfo.emailVerified) {
					val verifiedResult = authRepository.reloadAndCheckEmailVerified()
					if (verifiedResult.getOrNull() == true) {
						userRepository.updateEmailVerified(userId, true)
						userProfileInfo = userProfileInfo.copy(emailVerified = true)
					}
				}
				_uiState.update {
					it.copy(
						profile = userProfileInfo,
                        errorMessage = null,
						profileImageUrl = userProfileInfo.pfp
					)
				}
            } else {
                _uiState.update { it.copy(errorMessage = "No se pudo cargar el perfil") }
            }
        }
    }

	fun verifyOrSendEmailVerification() {
		val uid = authRepository.currentUser?.uid ?: return
		_uiState.update { it.copy(isVerifyingEmail = true, verificationMessage = null) }
		viewModelScope.launch {
			val checkResult = authRepository.reloadAndCheckEmailVerified()
			if (checkResult.getOrNull() == true) {
				userRepository.updateEmailVerified(uid, true)
				_uiState.update { state ->
					state.copy(
						profile = state.profile?.copy(emailVerified = true),
						isVerifyingEmail = false,
						verificationMessage = "¡Correo confirmado exitosamente!"
					)
				}
			} else {
				val sendResult = authRepository.sendEmailVerification()
				if (sendResult.isSuccess) {
					_uiState.update {
						it.copy(
							isVerifyingEmail = false,
							verificationMessage = "Te enviamos un correo de verificación. Confírmalo en tu bandeja y vuelve a pulsar este botón."
						)
					}
				} else {
					_uiState.update {
						it.copy(
							isVerifyingEmail = false,
							verificationMessage = sendResult.exceptionOrNull()?.message ?: "No se pudo enviar el correo de verificación"
						)
					}
				}
			}
		}
	}

	fun getUserReviews(userId: String) {
        _uiState.update { it.copy(isReviewsLoading = true, reviewsError = null) }
		viewModelScope.launch {
			val result = reviewRepository.getUserReviews(userId)

			if (result.isSuccess) {
				val userReviews = result.getOrNull() ?: emptyList()

				_uiState.update {
					it.copy(
						reviews = userReviews,
                        isReviewsLoading = false,
                        reviewsError = null
					)
				}
			} else {
				_uiState.update {
					it.copy(
						reviewsError = "No se pudieron cargar las reseñas",
                        isReviewsLoading = false
					)
				}
			}
		}
	}

	fun getUserLibrary(userId: String) {
		_uiState.update { it.copy(isLibraryLoading = true, libraryError = null) }
		viewModelScope.launch {
			val result = libraryRepository.getUserLibrary(userId)
			if (result.isSuccess) {
				_uiState.update {
					it.copy(
						libraryGames = result.getOrNull() ?: emptyList(),
						isLibraryLoading = false,
						libraryError = null
					)
				}
			} else {
				_uiState.update {
					it.copy(
						libraryError = "No se pudo cargar la colección",
						isLibraryLoading = false
					)
				}
			}
		}
	}

	fun selectProfileTab(tab: ProfileContentTab) {
		_uiState.update { it.copy(selectedTab = tab) }
	}

	fun selectLibraryFilter(status: LibraryStatus?) {
		_uiState.update { it.copy(selectedLibraryFilter = status) }
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

	fun deleteReview(reviewId: String, profileId: String) {
		viewModelScope.launch {
			_uiState.update { it.copy(isReviewDeleted = false) }

			val result = reviewRepository.deleteReview(reviewId)

			if (result.isSuccess) {
				_uiState.update { state ->
					state.copy(
						reviews = state.reviews?.filter { review ->
							review.idResenia != reviewId
						} ?: emptyList(),
						isReviewDeleted = true,
						errorMessage = null
					)
				}
			} else {
				_uiState.update {
					it.copy(
						errorMessage = "Error al eliminar la reseña"
					)
				}
			}
		}
	}

	fun updateReview(reviewId: String, review: CreateReviewDto) {
		viewModelScope.launch {
			_uiState.update { it.copy(isReviewUpdated = false) }

			val result = reviewRepository.updateReview(reviewId, review)
			if (result.isSuccess){
				_uiState.update {
					it.copy(
						isReviewUpdated = true,
						errorMessage = null
					)
				}
			} else {
				_uiState.update {
					it.copy(
						errorMessage = "Error al actualizar la reseña"
					)
				}
			}
		}
	}

	fun followOrUnfollow(targetUserId: String){
		val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?:""

		viewModelScope.launch {
			val result = userRepository.followOrUnfollow(currentUserId, targetUserId)
			if (result.isSuccess){
				val profile = uiState.value.profile ?: return@launch
				_uiState.value = _uiState.value.copy(
					profile = _uiState.value.profile?.copy(
						followers = if(profile.followed) profile.followers - 1 else profile.followers + 1,
						followed = !profile.followed
					)
				)
			}
		}
	}
}
