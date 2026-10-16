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

    fun updateBio(bio: String) {
        _uiState.update {
            it.copy(
                bio = bio,
                isSaved = false,
                errorMessage = null
            )
        }
    }

    fun saveBio() {
        if (_uiState.value.isLoading) return

        viewModelScope.launch {

            val userId = firebaseAuth.currentUser?.uid

            if (userId.isNullOrBlank()) {
                _uiState.update {
                    it.copy(
                        errorMessage = "Usuario no autenticado",
                        isSaved = false
                    )
                }
                return@launch
            }

            _uiState.update {
                it.copy(
                    isLoading = true,
                    isSaved = false,
                    errorMessage = null
                )
            }

            val bio = _uiState.value.bio

            userRepository.updateUserBio(userId, bio)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSaved = true,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSaved = false,
                            errorMessage = error.message
                                ?: "Error al guardar la biografía"
                        )
                    }
                }
        }
    }
}