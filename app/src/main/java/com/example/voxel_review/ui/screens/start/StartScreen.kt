package com.example.voxel_review.ui.screens.start

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.start.components.LoginButtons
import com.example.voxel_review.ui.screens.start.components.LoginForm
import com.example.voxel_review.ui.screens.start.components.StartHeader
import com.example.voxel_review.ui.utils.FondoPantalla

/**
 * Contenido visual de la pantalla de inicio.
 * Recibe el estado y los eventos necesarios sin acceder directamente al ViewModel.
 *
 * @param state Estado actual de la pantalla de inicio.
 * @param onUserNameChange Acción ejecutada cuando cambia el nombre de usuario.
 * @param onPasswordChange Acción ejecutada cuando cambia la contraseña.
 * @param onMostrarContrasenaChange Acción para mostrar u ocultar la contraseña.
 * @param logginButtonPressed Acción ejecutada al presionar el botón de inicio de sesión.
 * @param createAccountButtonPressed Acción ejecutada al presionar el botón de crear cuenta.
 * @param modifier Modificador para personalizar el contenido.
 */
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

/**
 * Pantalla de inicio conectada al ViewModel.
 * Observa el estado de la interfaz y conecta las acciones del usuario
 * con las funciones correspondientes del ViewModel.
 *
 * @param startViewModel ViewModel encargado de administrar el estado de la pantalla.
 * @param logginButtonPressed Acción ejecutada después de un inicio de sesión válido.
 * @param createAccountButtonPressed Acción ejecutada para navegar a la creación de cuenta.
 * @param modifier Modificador para personalizar la pantalla.
 */
@Composable
fun StartScreen(
    startViewModel: StartViewModel,
    logginButtonPressed: () -> Unit,
    createAccountButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Convierte el StateFlow del ViewModel en un estado observable por Compose.
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
                // Solo continúa si los datos de inicio de sesión son válidos.
                if (startViewModel.login()) {
                    logginButtonPressed()
                }
            },
            createAccountButtonPressed = createAccountButtonPressed
        )
    }
}

/**
 * Vista previa de la pantalla de inicio.
 */
@Composable
@Preview
fun StartScreenPreview() {
    StartScreen(
        startViewModel = viewModel(),
        logginButtonPressed = {},
        createAccountButtonPressed = {}
    )
}