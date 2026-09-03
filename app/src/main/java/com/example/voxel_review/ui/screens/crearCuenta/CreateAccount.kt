package com.example.voxel_review.ui.screens.crearCuenta

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
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
@Composable
fun CreateAccountContent(
    state: CreateAccountState,
    onUserNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTerminosAceptadosChange: () -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    unirseButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = (-55).dp)
    ) {

        CreateAcountHeader()

        FomulariosCreateAcount(
            state = state,
            onUserNameChange = onUserNameChange,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,
            onTerminosAceptadosChange = onTerminosAceptadosChange,
            unirseButtonPressed = unirseButtonPressed,
            modifier = Modifier
        )
    }
}

@Composable
fun CreateAccountScreen(
    createAccountViewModel: CreateAccountViewModel,
    unirseButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by createAccountViewModel.uiState.collectAsState()

    Box(modifier = modifier.fillMaxWidth()){
        FondoPantalla()
        CreateAccountContent(
            state = state,
            onUserNameChange = createAccountViewModel::updateUsername,
            onEmailChange = createAccountViewModel::updateEmail,
            onPasswordChange = createAccountViewModel::updatePassword,
            onTerminosAceptadosChange = createAccountViewModel::updateTerminosAceptados,
            onMostrarContrasenaChange = createAccountViewModel::updateMostrarContrasena,
            unirseButtonPressed = {
                if (createAccountViewModel.login()){
                    unirseButtonPressed()
                }
            },
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Preview (showBackground = true)
@Composable
private fun CreateAccountScreenPreview() {
    CreateAccountScreen(
        createAccountViewModel = viewModel(),
        unirseButtonPressed = {}
    )
}

