package com.example.voxel_review.ui.screens.forgotPassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de administrar el estado y la lógica de recuperación de contraseña.
 */
@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForgotPasswordState())
    val uiState: StateFlow<ForgotPasswordState> = _uiState.asStateFlow()

    /**
     * Actualiza el correo electrónico ingresado y limpia mensajes previos.
     *
     * @param input Nuevo valor del correo electrónico.
     */
    fun updateCorreo(input: String) {
        _uiState.update {
            it.copy(
                correo = input,
                errorMessage = "",
                isEmailSent = false
            )
        }
    }

    /**
     * Valida el correo ingresado y envía el enlace de recuperación mediante Firebase Authentication.
     */
    fun sendResetEmail() {
        val email = _uiState.value.correo.trim()

        if (email.isEmpty()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Por favor, ingresa tu correo electrónico",
                    isEmailSent = false
                )
            }
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Ingresa un correo electrónico válido",
                    isEmailSent = false
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = "",
                    isEmailSent = false
                )
            }

            val result = authRepository.sendPasswordResetEmail(email)

            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isEmailSent = true,
                        errorMessage = ""
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isEmailSent = false,
                        errorMessage = result.exceptionOrNull()?.message
                            ?: "Error al enviar el correo de recuperación"
                    )
                }
            }
        }
    }
}
