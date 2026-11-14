package com.example.voxel_review.ui.screens.start

import androidx.lifecycle.ViewModel
import com.example.voxel_review.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
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
            it.copy(correo = input, errorMessage = "", mostrarMensaje = false)
        }
    }

    /**
     * Actualiza la contraseña ingresada.
     *
     * @param input Nuevo valor de la contraseña.
     */
    fun updatePassword(input: String) {
        _uiState.update {
            it.copy(password = input, errorMessage = "", mostrarMensaje = false)
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

    fun login(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.loading) return

        val correo = state.correo.trim()
        val password = state.password
        val validationError = when {
            correo.isEmpty() || password.isEmpty() -> "Completa todos los campos."
            password.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
            else -> null
        }
        if (validationError != null) {
            _uiState.update { it.copy(errorMessage = validationError, mostrarMensaje = true) }
            return
        }

        _uiState.update { it.copy(loading = true, errorMessage = "", mostrarMensaje = false) }
        viewModelScope.launch {
            val result = authRepository.signIn(correo, password)
            val errorMessage = when (result.exceptionOrNull()) {
                null -> ""
                is FirebaseAuthInvalidUserException -> "Usuario no existe."
                is FirebaseAuthInvalidCredentialsException -> "Credenciales incorrectas."
                is FirebaseTooManyRequestsException ->
                    "Demasiados intentos. Vuelve a intentarlo más tarde."
                else -> "No se pudo iniciar sesión. Inténtalo de nuevo."
            }
            _uiState.update {
                it.copy(
                    loading = false,
                    errorMessage = errorMessage,
                    mostrarMensaje = errorMessage.isNotEmpty()
                )
            }
            if (result.isSuccess) onSuccess()
        }
    }
}
