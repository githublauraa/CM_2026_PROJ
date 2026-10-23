package com.example.voxel_review.ui.auth

import org.junit.Assert.*
import org.junit.Test

class AuthErrorMessageTest {
    @Test fun hidesTechnicalErrors() {
        val result = authErrorMessage(IllegalStateException("sensitive backend detail"))
        assertFalse(result.contains("sensitive"))
        assertTrue(result.contains("iniciar sesión"))
        assertTrue(authErrorMessage(null, true).contains("crear la cuenta"))
    }
    @Test fun usesSameMessageForUnknownUserAndWrongPassword() {
        assertEquals(
            authCodeMessage("ERROR_USER_NOT_FOUND", false),
            authCodeMessage("ERROR_WRONG_PASSWORD", false)
        )
    }
    @Test fun providesActionForNetworkAndDuplicateEmail() {
        assertTrue(authCodeMessage("ERROR_NETWORK_REQUEST_FAILED", false).contains("conexión"))
        assertTrue(authCodeMessage("ERROR_EMAIL_ALREADY_IN_USE", true).contains("Inicia sesión"))
    }
}
