package com.example.voxel_review.ui.auth

object AuthValidation {
    fun email(value: String): String? = when {
        value.isBlank() -> "Ingresa tu correo electrónico."
        value.trim().length > 254 || !Regex("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$").matches(value.trim()) -> "Ingresa un correo válido, por ejemplo: nombre@correo.com."
        else -> null
    }
    fun password(value: String, registration: Boolean = false): String? = when {
        value.isBlank() -> "Ingresa tu contraseña."
        registration && value.length < 6 -> "La contraseña debe tener al menos 6 caracteres."
        registration && value.length > 128 -> "La contraseña no puede superar 128 caracteres."
        else -> null
    }
    fun username(value: String): String? = when {
        value.isBlank() -> "Ingresa tu nombre de usuario."
        value.trim().length !in 3..30 -> "El nombre de usuario debe tener entre 3 y 30 caracteres."
        value.any { it.isISOControl() } -> "El nombre de usuario contiene caracteres no válidos."
        else -> null
    }
}
