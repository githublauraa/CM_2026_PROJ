package com.example.voxel_review.ui.screens.start

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de inicio de sesión.
 */
@HiltViewModel
class StartViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    // Estado interno modificable únicamente desde el ViewModel.
    private val _uiState = MutableStateFlow(StartState())

    // Estado público de solo lectura que puede observar la interfaz.
    val uiState: StateFlow<StartState> = _uiState

    /**
     * Actualiza el nombre de usuario ingresado.
     *
     * @param input Nuevo valor del nombre de usuario.
     */
    fun updateCorreo(input: String) {
        _uiState.update {
            it.copy(correo = input)
        }
    }

    /**
     * Actualiza la contraseña ingresada.
     *
     * @param input Nuevo valor de la contraseña.
     */
    fun updatePassword(input: String) {
        _uiState.update {
            it.copy(password = input)
        }
    }

    /**
     * Cambia la visibilidad de la contraseña.
     */
    fun updateMostrarContrasena() {
        _uiState.update {
            it.copy(mostrarContrasena = !it.mostrarContrasena)
        }
    }

    /**
     * Valida los datos ingresados antes de realizar el inicio de sesión.
     *
     * @return `true` si los datos son válidos o `false` si existe algún error.
     */
    fun login(onSuccess: () -> Unit) {
        if (
            _uiState.value.correo.isEmpty() ||
            _uiState.value.password.isEmpty()
        ) {
            _uiState.update {
                it.copy(errorMessage = "Por favor, completa todos los campos")
            }
            return
        }

        if (_uiState.value.password.length < 6) {
            _uiState.update {
                it.copy(errorMessage = "La contraseña debe ser minimo de 6 caracteres")
            }
            return
        }

        viewModelScope.launch {
            try {
                authRepository.signIn(
                    _uiState.value.correo,
                    _uiState.value.password
                )

                onSuccess()

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "Credenciales incorrectas"
                    )
                }
            }
        }
    }
}