package com.example.voxel_review.ui.screens.crearCuenta

data class CreateAccountState(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val mostrarContrasena: Boolean = false,
    val terminosAceptados: Boolean = false,
    val errorMessage: String = ""
)
