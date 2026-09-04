package com.example.voxel_review.ui.screens.crearCuenta

/**
 * Representa el estado de la interfaz de la pantalla de creación de cuenta.
 * Almacena los datos ingresados por el usuario y los estados necesarios
 * para actualizar la interfaz.
 *
 * @param userName Nombre de usuario ingresado.
 * @param email Correo electrónico ingresado.
 * @param password Contraseña ingresada.
 * @param mostrarContrasena Indica si la contraseña debe mostrarse.
 * @param terminosAceptados Indica si el usuario aceptó los términos y condiciones.
 * @param errorMessage Mensaje de error generado durante la validación.
 */
data class CreateAccountState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val mostrarContrasena: Boolean = false,
    val terminosAceptados: Boolean = false,
    val errorMessage: String = ""
)