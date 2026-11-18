package com.example.voxel_review.ui.screens.crearCuenta

import com.example.voxel_review.R
import com.example.voxel_review.data.localization.AppStrings
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import com.example.voxel_review.data.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.voxel_review.data.repository.UserRepository


/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de creación de cuenta.
 */
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val strings: AppStrings
): ViewModel() {

    // Estado interno modificable únicamente desde el ViewModel.
    private val _uiState = MutableStateFlow(CreateAccountState())

    // Estado público de solo lectura que puede observar la interfaz.
    val uiState: StateFlow<CreateAccountState> = _uiState

    fun updateBio(input: String){
        _uiState.update {
            it.copy(bio = input)
        }
    }


    fun updateUsername(input: String) {
        _uiState.update {
            it.copy(username = input)
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
    fun createAcount(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.loading) return

        val email = state.email.trim()
        val error = when {
            email.isEmpty() -> strings[R.string.enter_email]
            state.password.length < 6 -> strings[R.string.password_at_least_six]
            !state.terminosAceptados -> strings[R.string.accept_terms_required]
            else -> null
        }
        if (error != null) {
            _uiState.update { it.copy(errorMessage = error, mostrarMensaje = true) }
            return
        }

        _uiState.update { it.copy(loading = true, errorMessage = "", mostrarMensaje = false) }
        viewModelScope.launch {

            val result = authRepository.signUp(
                email,
                state.password
            )

            if (result.isSuccess) {

                val userId = authRepository.currentUser?.uid

                if (userId == null) {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            errorMessage = strings[R.string.error_user_id],
                            mostrarMensaje = true
                        )
                    }
                    return@launch
                }

                val registerResult = userRepository.registerUser(
                    nombreUsuario = state.username,
                    bio = state.bio,
                    userId = userId,
                    photoUrl = null
                )

                if (registerResult.isSuccess) {
                    val verification = authRepository.sendEmailVerification()
                    _uiState.update {
                        it.copy(loading = false, verificationError = verification.exceptionOrNull()?.message)
                    }
                    onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            errorMessage = registerResult.exceptionOrNull()?.message
                                ?: strings[R.string.error_profile_save],
                            mostrarMensaje = true
                        )
                    }
                }
            } else {
                _uiState.update {
                    it.copy(
                        loading = false,
                        errorMessage = result.exceptionOrNull()?.message ?: strings[R.string.error_create_account],
                        mostrarMensaje = true
                    )
                }
            }
        }
    }
}
