package com.example.voxel_review.ui.auth

import org.junit.Assert.*
import org.junit.Test

class AuthValidationTest {
    @Test fun emailRejectsMissingOrMalformedAddresses() {
        listOf("", "   ", "nombre", "a@", "@correo.com", "a b@correo.com", "a@correo").forEach {
            assertNotNull("Debe rechazar: $it", AuthValidation.email(it))
        }
        assertNull(AuthValidation.email("  persona+app@correo.com  "))
    }
    @Test fun loginDoesNotRejectExistingShortPasswordsLocally() {
        assertNull(AuthValidation.password("abc"))
        assertNotNull(AuthValidation.password("   "))
    }
    @Test fun registrationChecksPasswordLengthWithoutTrimmingIt() {
        assertNotNull(AuthValidation.password("12345", true))
        assertNull(AuthValidation.password("123456", true))
        assertNull(AuthValidation.password(" abcde", true))
        assertNotNull(AuthValidation.password("a".repeat(129), true))
    }
    @Test fun usernameChecksTrimmedLengthAndControlCharacters() {
        assertNotNull(AuthValidation.username("   "))
        assertNotNull(AuthValidation.username(" ab "))
        assertNull(AuthValidation.username(" Gamer José "))
        assertNotNull(AuthValidation.username("a".repeat(31)))
        assertNotNull(AuthValidation.username("abc\nxyz"))
    }
}
