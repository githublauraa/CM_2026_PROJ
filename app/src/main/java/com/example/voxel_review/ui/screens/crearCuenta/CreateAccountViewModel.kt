package com.example.voxel_review.ui.screens.crearCuenta

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de creación de cuenta.
 */
class CreateAccountViewModel : ViewModel() {

    // Estado interno modificable únicamente desde el ViewModel.
    private val _uiState = MutableStateFlow(CreateAccountState())

    // Estado público de solo lectura que puede observar la interfaz.
    val uiState: StateFlow<CreateAccountState> = _uiState

    /**
     * Actualiza el nombre de usuario ingresado.
     */
    fun updateUsername(input: String) {
        _uiState.update {
            it.copy(userName = input)
        }
    }

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
    fun createAcount(): Boolean {

        // Verifica que los campos obligatorios tengan información.
        if (
            _uiState.value.userName.isEmpty() ||
            _uiState.value.email.isEmpty()
        ) {
            _uiState.update {
                it.copy(
                    errorMessage = "Por favor, completa todos los campos"
                )
            }
            return false
        }

        // Verifica la longitud mínima de la contraseña.
        if (_uiState.value.password.length < 6) {
            _uiState.update {
                it.copy(
                    errorMessage = "La contraseña debe tener al menos 6 caracteres"
                )
            }
            return false
        }

        // Verifica que el usuario haya aceptado los términos y condiciones.
        if (!_uiState.value.terminosAceptados) {
            _uiState.update {
                it.copy(
                    errorMessage = "Por favor, acepta los términos y condiciones"
                )
            }
            return false
        }

        // Si todas las validaciones son correctas, elimina cualquier error anterior.
        _uiState.update {
            it.copy(errorMessage = "")
        }

        return true
    }
}