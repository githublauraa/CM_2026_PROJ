package com.example.voxel_review.ui.screens.editProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileState())
    val uiState: StateFlow<EditProfileState> = _uiState

    fun loadProfile() {
        if (_uiState.value.isProfileLoading || _uiState.value.isProfileLoaded) return

        val userId = firebaseAuth.currentUser?.uid
        if (userId.isNullOrBlank()) {
            _uiState.update { it.copy(errorMessage = "Usuario no autenticado") }
            return
        }

        _uiState.update { it.copy(isProfileLoading = true, errorMessage = null) }
        viewModelScope.launch {
            userRepository.getUserById(userId)
                .onSuccess { profile ->
                    _uiState.update {
                        it.copy(
                            bio = profile.biografia,
                            username = profile.nick,
                            isProfileLoading = false,
                            isProfileLoaded = true
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isProfileLoading = false,
                            errorMessage = error.message ?: "Error al cargar el perfil"
                        )
                    }
                }
        }
    }

    fun updateBio(bio: String) {
        if (!_uiState.value.isProfileLoaded || _uiState.value.isLoading) return
        _uiState.update {
            it.copy(
                bio = bio,
                isSaved = false,
                errorMessage = null
            )
        }
    }

    fun updateUsername(username: String) {
        if (!_uiState.value.isProfileLoaded || _uiState.value.isLoading) return
        _uiState.update {
            it.copy(
                username = username,
                isSaved = false,
                errorMessage = null
            )
        }
    }

    fun saveProfile() {
        val state = _uiState.value
        if (state.isLoading || !state.isProfileLoaded) return

        val userId = firebaseAuth.currentUser?.uid
        if (userId.isNullOrBlank()) {
            _uiState.update {
                it.copy(errorMessage = "Usuario no autenticado")
            }
            return
        }

        _uiState.update {
            it.copy(
                isLoading = true,
                isSaved = false,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            val bioResult = userRepository.updateUserBio(userId, state.bio)

            if (bioResult.isFailure) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = bioResult.exceptionOrNull()?.message
                            ?: "Error al guardar la biografía"
                    )
                }
                return@launch
            }

            val usernameResult =
                userRepository.updateUsername(userId, state.username)

            usernameResult
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSaved = true
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                                ?: "La biografía se guardó, pero no se pudo guardar el username"
                        )
                    }
                }
        }
    }


}
