package com.example.voxel_review.ui.screens.crearCuenta

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.screens.crearCuenta.components.RegisterForm
import com.example.voxel_review.ui.screens.start.components.StartHeader
import com.example.voxel_review.ui.utils.AuthScreenLayout

@Composable
private fun CreateAccountContent(
    state: CreateAccountState,
    onEmailChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onTerminosAceptadosChange: () -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    onSubmit: () -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthScreenLayout(modifier) {
        StartHeader()
        RegisterForm(
            state = state,
            onUsernameChange = onUsernameChange,
            onBioChange = onBioChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,
            onTerminosAceptadosChange = onTerminosAceptadosChange,
            onSubmit = onSubmit,
            onBackToLogin = onBackToLogin
        )
    }
}

@Composable
fun CreateAccountScreen(
    createAccountViewModel: CreateAccountViewModel,
    unirseButtonPressed: () -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by createAccountViewModel.uiState.collectAsState()
    CreateAccountContent(
        state = state,
        onBioChange = createAccountViewModel::updateBio,
        onUsernameChange = createAccountViewModel::updateUsername,
        onEmailChange = createAccountViewModel::updateEmail,
        onPasswordChange = createAccountViewModel::updatePassword,
        onTerminosAceptadosChange = createAccountViewModel::updateTerminosAceptados,
        onMostrarContrasenaChange = createAccountViewModel::updateMostrarContrasena,
        onSubmit = { createAccountViewModel.createAcount(unirseButtonPressed) },
        onBackToLogin = onBackToLogin,
        modifier = modifier
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
private fun CreateAccountScreenPreview() {
    CreateAccountContent(
        state = CreateAccountState(),
        onBioChange = {},
        onUsernameChange = {},
        onEmailChange = {},
        onPasswordChange = {},
        onTerminosAceptadosChange = {},
        onMostrarContrasenaChange = {},
        onSubmit = {},
        onBackToLogin = {}
    )
}
