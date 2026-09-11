package com.example.voxel_review.ui.screens.crearCuenta

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import com.example.voxel_review.data.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de creación de cuenta.
 */
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    // Estado interno modificable únicamente desde el ViewModel.
    private val _uiState = MutableStateFlow(CreateAccountState())

    // Estado público de solo lectura que puede observar la interfaz.
    val uiState: StateFlow<CreateAccountState> = _uiState


    /**
     * Actualiza el correo electrónico ingresado.
     */
    fun updateEmail(input: String) {
        _uiState.update {
            it.copy(email = input)
        }
    }

    /**
     * Actualiza la contraseña ingresada.
     */
    fun updatePassword(input: String) {
        _uiState.update {
            it.copy(password = input)
        }
    }

    /**
     * Alterna la visibilidad de la contraseña.
     */
    fun updateMostrarContrasena() {
        _uiState.update {
            it.copy(mostrarContrasena = !it.mostrarContrasena)
        }
    }

    /**
     * Alterna el estado de aceptación de los términos y condiciones.
     */
    fun updateTerminosAceptados() {
        _uiState.update {
            it.copy(terminosAceptados = !it.terminosAceptados)
        }
    }

    /**
     * Valida los datos necesarios para crear una cuenta.
     * Si encuentra un error, actualiza el mensaje mostrado en la interfaz.
     *
     * @return `true` si los datos son válidos, `false` en caso contrario.
     */
    fun createAcount(onSuccess: () -> Unit) {

        if (_uiState.value.email.isEmpty()) {
            _uiState.update {
                it.copy(
                    errorMessage = "Por favor, completa todos los campos"
                )
            }
            return
        }

        if (_uiState.value.password.length < 6) {
            _uiState.update {
                it.copy(
                    errorMessage = "La contraseña debe tener al menos 6 caracteres"
                )
            }
            return
        }

        if (!_uiState.value.terminosAceptados) {
            _uiState.update {
                it.copy(
                    errorMessage = "Por favor, acepta los términos y condiciones"
                )
            }
            return
        }

        viewModelScope.launch {
            try {
                authRepository.signUp(
                    _uiState.value.email,
                    _uiState.value.password
                )

                // Solo navega si Firebase respondió correctamente
                onSuccess()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "Error al crear la cuenta"
                    )
                }
            }
        }
    }
}