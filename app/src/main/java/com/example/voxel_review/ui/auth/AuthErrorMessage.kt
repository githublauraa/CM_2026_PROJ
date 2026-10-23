package com.example.voxel_review.ui.auth

import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException

fun authErrorMessage(error: Throwable?, registration: Boolean = false): String = when (error) {
    is FirebaseNetworkException, is IOException -> "No pudimos conectarnos. Revisa tu conexión a internet e inténtalo de nuevo."
    is FirebaseTooManyRequestsException -> "Demasiados intentos. Espera unos minutos antes de volver a intentarlo."
    is FirebaseAuthException -> authCodeMessage(error.errorCode, registration)
    is FirebaseFirestoreException -> when (error.code) {
        FirebaseFirestoreException.Code.UNAVAILABLE, FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> "No se pudo guardar el perfil. Revisa tu conexión y vuelve a intentarlo."
        FirebaseFirestoreException.Code.PERMISSION_DENIED -> "No tienes permiso para guardar el perfil. Contacta al administrador."
        else -> "No pudimos guardar tu perfil. Inténtalo de nuevo."
    }
    else -> if (registration) "No pudimos crear la cuenta. Inténtalo de nuevo." else "No pudimos iniciar sesión. Inténtalo de nuevo."
}

internal fun authCodeMessage(code: String, registration: Boolean): String = when (code) {
        "ERROR_INVALID_EMAIL" -> "El correo electrónico no tiene un formato válido."
        "ERROR_EMAIL_ALREADY_IN_USE" -> "Ya existe una cuenta con este correo. Inicia sesión con ella."
        "ERROR_WEAK_PASSWORD" -> "La contraseña es demasiado débil. Usa al menos 6 caracteres y combina letras, números y símbolos."
        "ERROR_USER_DISABLED" -> "Esta cuenta está deshabilitada. Contacta al administrador."
        "ERROR_USER_NOT_FOUND", "ERROR_WRONG_PASSWORD", "ERROR_INVALID_CREDENTIAL", "ERROR_INVALID_LOGIN_CREDENTIALS" -> "El correo o la contraseña son incorrectos. Revisa tus datos e inténtalo de nuevo."
        "ERROR_OPERATION_NOT_ALLOWED" -> "El acceso con correo y contraseña no está disponible. Contacta al administrador."
        "ERROR_NETWORK_REQUEST_FAILED" -> "No pudimos conectarnos. Revisa tu conexión a internet e inténtalo de nuevo."
        "ERROR_TOO_MANY_REQUESTS" -> "Demasiados intentos. Espera unos minutos antes de volver a intentarlo."
        else -> if (registration) "No pudimos crear la cuenta. Inténtalo de nuevo." else "No pudimos iniciar sesión. Inténtalo de nuevo."
    }
