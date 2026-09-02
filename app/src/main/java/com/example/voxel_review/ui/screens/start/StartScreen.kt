package com.example.voxel_review.ui.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.screens.start.components.LoginButtons
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.start.components.StartHeader
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.screens.start.components.LoginForm
import kotlin.math.log


@Composable
fun StartScreenContent(
    state: StartState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        StartHeader(
            modifier = Modifier.align(Alignment.TopCenter)
        )

        LoginForm(
            state = state,
            onUserNameChange = onUserNameChange,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = -30.dp)
        )

        LoginButtons(
            logginButtonPressed = logginButtonPressed,
            createAccountButtonPressed = createAccountButtonPressed,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 30.dp)
        )
    }
}

@Composable
fun StartScreen(
    startViewModel: StartViewModel,
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by startViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        FondoPantalla()

        StartScreenContent(
            state = state,
            onUserNameChange = startViewModel::updateUsername,
            onPasswordChange = startViewModel::updatePassword,
            onMostrarContrasenaChange = startViewModel::updateMostrarContrasena,
            logginButtonPressed = {
                if (startViewModel.login())
                    logginButtonPressed()
            },
            createAccountButtonPressed = createAccountButtonPressed
        )
    }
}

@Composable
@Preview
fun StartScreenPreview() {
    StartScreen(
        startViewModel = viewModel (),
        logginButtonPressed = {},
        createAccountButtonPressed = {}
    )
}