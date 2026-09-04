package com.example.voxel_review.ui.screens.start

/**
 * Representa el estado de la interfaz de la pantalla de inicio.
 * Almacena los datos ingresados por el usuario y los estados necesarios
 * para actualizar la interfaz.
 *
 * @param usuario Nombre de usuario ingresado.
 * @param password Contraseña ingresada.
 * @param mostrarContrasena Indica si la contraseña debe mostrarse.
 * @param errorMessage Mensaje de error generado durante la validación.
 */
data class StartState(
    val usuario: String = "",
    val password: String = "",
    val mostrarContrasena: Boolean = false,
    val errorMessage: String = ""
)