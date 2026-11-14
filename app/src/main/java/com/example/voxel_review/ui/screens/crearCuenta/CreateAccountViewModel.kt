package com.example.voxel_review.ui.screens.crearCuenta

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import jakarta.inject.Inject
import com.example.voxel_review.data.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import com.example.voxel_review.data.injection.FirebaseHiltModule.auth
import kotlinx.coroutines.launch
import com.example.voxel_review.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.tasks.await


/**
 * ViewModel encargado de administrar el estado y la lógica
 * de la pantalla de creación de cuenta.
 */
@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val auth: FirebaseAuth
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
            email.isEmpty() -> "Ingresa tu correo electrónico"
            state.password.length < 6 -> "La contraseña debe tener al menos 6 caracteres"
            !state.terminosAceptados -> "Por favor, acepta los términos y condiciones"
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
                            errorMessage = "No se pudo obtener el ID del usuario",
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
                    // 📧 Enviar correo de verificación al registrar exitosamente
                    try {
                        auth.currentUser?.sendEmailVerification()?.await()
                        //Log.d("CreateAccountVM", "Correo de verificación enviado exitosamente")
                    } catch (e: Exception) {
                        // Opcional: manejar si falla el envío de correo de verificación
                        //Log.e("CreateAccountVM", "Error al enviar verificación de correo", e)
                    }

                    _uiState.update { it.copy(loading = false) }
                    onSuccess()
                } else {
                    _uiState.update {
                        it.copy(
                            loading = false,
                            errorMessage = registerResult.exceptionOrNull()?.message
                                ?: "Error al guardar el usuario en Firestore",
                            mostrarMensaje = true
                        )
                    }
                }
            } else {


                _uiState.update {
                    val e = result.exceptionOrNull()

                    when (e){
                        is FirebaseAuthUserCollisionException -> {
                            it.copy(
                                loading = false,
                                errorMessage = "El correo electrónico ya está en uso",
                                mostrarMensaje = true
                            )
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            it.copy(
                                loading = false,
                                errorMessage = "El correo no es valido",
                                mostrarMensaje = true
                            )
                        }
                        else -> {
                            //Log.e("RegistroE2E", "Error al registrar", e)
                            it.copy(
                                loading = false,
                                errorMessage = "Error al registrar un usuario",
                                mostrarMensaje = true
                            )
                        }
                    }
                }

            }
        }
    }
}