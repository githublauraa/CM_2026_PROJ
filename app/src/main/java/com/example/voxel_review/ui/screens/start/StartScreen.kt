package com.example.voxel_review.ui.screens.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.screens.start.components.LoginForm
import com.example.voxel_review.ui.screens.start.components.MensajeTerminos
import com.example.voxel_review.ui.screens.start.components.StartHeader
import com.example.voxel_review.ui.utils.AuthScreenLayout

@Composable
fun StartScreenContent(
    state: StartState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    forgotPasswordButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val submit = {
        if (!state.loading) {
            focusManager.clearFocus()
            logginButtonPressed()
        }
    }

    AuthScreenLayout(modifier) {
        StartHeader()
        LoginForm(
            state = state,
            onUserNameChange = onUserNameChange,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,
            onForgotPasswordClick = forgotPasswordButtonPressed,
            onSubmit = submit,
            onCreateAccount = createAccountButtonPressed
        )
        MensajeTerminos()
    }
}

@Composable
fun StartScreen(
    startViewModel: StartViewModel,
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    forgotPasswordButtonPressed: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by startViewModel.uiState.collectAsState()
    StartScreenContent(
        state = state,
        onUserNameChange = startViewModel::updateCorreo,
        onPasswordChange = startViewModel::updatePassword,
        onMostrarContrasenaChange = startViewModel::updateMostrarContrasena,
        logginButtonPressed = { startViewModel.login(logginButtonPressed) },
        createAccountButtonPressed = createAccountButtonPressed,
        forgotPasswordButtonPressed = forgotPasswordButtonPressed,
        modifier = modifier
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun StartScreenPreview() {
    StartScreenContent(
        state = StartState(),
        onUserNameChange = {},
        onPasswordChange = {},
        onMostrarContrasenaChange = {},
        logginButtonPressed = {},
        createAccountButtonPressed = {}
    )
}
