package com.example.voxel_review.ui.screens.crearCuenta

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.FondoPantalla
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.ui.screens.crearCuenta.components.CreateAcountHeader
import com.example.voxel_review.ui.screens.crearCuenta.components.FomulariosCreateAcount

/**
 * Contenido visual de la pantalla de creación de cuenta.
 * Recibe el estado y los eventos desde la pantalla sin acceder directamente al ViewModel.
 *
 * @param state Estado actual de la pantalla.
 * @param onUserNameChange Acción ejecutada cuando cambia el nombre de usuario.
 * @param onEmailChange Acción ejecutada cuando cambia el correo electrónico.
 * @param onPasswordChange Acción ejecutada cuando cambia la contraseña.
 * @param onTerminosAceptadosChange Acción para cambiar la aceptación de los términos.
 * @param onMostrarContrasenaChange Acción para mostrar u ocultar la contraseña.
 * @param unirseButtonPressed Acción ejecutada al presionar el botón de registro.
 * @param modifier Modificador para personalizar el contenido.
 */
@Composable
fun CreateAccountContent(
    state: CreateAccountState,
    onEmailChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onTerminosAceptadosChange: () -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    unirseButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier.fillMaxSize().imePadding().verticalScroll(rememberScrollState()).padding(bottom = 24.dp)

    ) {
        CreateAcountHeader()

        FomulariosCreateAcount(
            onBioChange = onBioChange,
            onUsernameChange = onUsernameChange,
            state = state,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,
            onTerminosAceptadosChange = onTerminosAceptadosChange,
            unirseButtonPressed = unirseButtonPressed,
            modifier = Modifier
        )
    }
}

/**
 * Pantalla de creación de cuenta conectada al ViewModel.
 * Observa el estado y conecta los eventos de la interfaz con las funciones del ViewModel.
 *
 * @param createAccountViewModel ViewModel encargado del estado y la lógica de la pantalla.
 * @param unirseButtonPressed Acción ejecutada después de crear la cuenta correctamente.
 * @param modifier Modificador para personalizar la pantalla.
 */
@Composable
fun CreateAccountScreen(
    createAccountViewModel: CreateAccountViewModel,
    unirseButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Convierte el StateFlow del ViewModel en un estado observable por Compose.
    val state by createAccountViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        FondoPantalla()

        CreateAccountContent(
            onBioChange = createAccountViewModel::updateBio,
            onUsernameChange = createAccountViewModel::updateUsername,
            state = state,
            onEmailChange = createAccountViewModel::updateEmail,
            onPasswordChange = createAccountViewModel::updatePassword,
            onTerminosAceptadosChange = createAccountViewModel::updateTerminosAceptados,
            onMostrarContrasenaChange = createAccountViewModel::updateMostrarContrasena,

            // Solo continúa con la acción externa si la validación es correcta.
            unirseButtonPressed = {
                createAccountViewModel.createAcount {
                    unirseButtonPressed()
                }
            },

            modifier = modifier.fillMaxWidth()
        )
    }
}

/**
 * Vista previa de la pantalla de creación de cuenta.
 */
@Preview(showBackground = true)
@Composable
private fun CreateAccountScreenPreview() {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        FondoPantalla()

        CreateAccountContent(
            onBioChange = {},
            onUsernameChange = {},
            state = CreateAccountState(),
            onEmailChange = {},
            onPasswordChange = {},
            onTerminosAceptadosChange = {},
            onMostrarContrasenaChange = {},
            unirseButtonPressed = {}
        )
    }
}