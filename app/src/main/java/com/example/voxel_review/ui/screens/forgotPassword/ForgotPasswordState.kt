package com.example.voxel_review.ui.screens.forgotPassword

/**
 * Representa el estado de la interfaz de la pantalla de recuperación de contraseña.
 *
 * @param correo Correo electrónico ingresado por el usuario.
 * @param isLoading Indica si se está enviando la solicitud de restablecimiento.
 * @param errorMessage Mensaje de error mostrado en caso de validación o fallo de red.
 * @param isEmailSent Indica si el correo de recuperación fue enviado exitosamente.
 */
data class ForgotPasswordState(
    val correo: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val isEmailSent: Boolean = false
)
