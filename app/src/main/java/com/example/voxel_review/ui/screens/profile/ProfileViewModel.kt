package com.example.voxel_review.ui.screens.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.data.profile.ProfileInfo
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.StorageRepository
import com.example.voxel_review.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val authRepository: AuthRepository,
	private val storageRepository: StorageRepository,
	private val userRepository: UserRepository
) : ViewModel() {

	private val _uiState = MutableStateFlow(
		ProfileState(
			email = authRepository.currentUser?.email ?: "",
			profileImageUrl = authRepository.currentUser?.photoUrl?.toString() ?: ""
		)
	)

	val uiState: StateFlow<ProfileState> = _uiState

	private var lastProfileId: String? = null

	fun getProfileById(userId: String) {
		lastProfileId = userId
		viewModelScope.launch {
			_uiState.update { it.copy(isLoading = true, errorMessage = null) }

			val result = getUserWithRetry(userId)

			result.onSuccess { userProfileInfo ->
				_uiState.update {
					it.copy(profile = userProfileInfo, isLoading = false, errorMessage = null)
				}
			}.onFailure { error ->
				Log.e(TAG, "No se pudo cargar el perfil $userId", error)
				_uiState.update {
					it.copy(
						isLoading = false,
						errorMessage = "No se pudo conectar con el servidor",
						profile = it.profile ?: LocalProfileProvider.profiles[0],
					)
				}
			}
		}
	}

	fun retry() {
		val userId = lastProfileId ?: return
		getProfileById(userId)
	}

	/**
	 * Reintenta la carga del perfil ante fallos transitorios de red
	 * (timeout, sin conexión, servidor aún no disponible) con espera progresiva.
	 */
	private suspend fun getUserWithRetry(userId: String, maxAttempts: Int = 3): Result<ProfileInfo> {
		var lastError: Throwable? = null

		for (attempt in 1..maxAttempts) {
			val result = userRepository.getUserById(userId)
			result.onSuccess { return Result.success(it) }

			val error = result.exceptionOrNull()
			lastError = error

			// Solo reintenta fallos de red; un error HTTP/JSON no se resuelve esperando
			if (error !is IOException || attempt == maxAttempts) break

			val waitMs = 1000L * attempt
			Log.w(TAG, "Fallo de red, reintento $attempt/$maxAttempts en ${waitMs}ms")
			delay(waitMs)
		}

		return Result.failure(lastError ?: IllegalStateException("Error desconocido al cargar el perfil"))
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

	companion object {
		private const val TAG = "ProfileViewModel"
	}
}
