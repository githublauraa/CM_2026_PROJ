package com.example.voxel_review.ui.screens.home

data class StartState(
    val usuario: String = "",
    val password: String  = "",
    val mostrarContrasena: Boolean = false,
    val errorMessage: String = ""
)
