
package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.layout.*
import com.example.voxel_review.ui.auth.AuthFieldError
import com.example.voxel_review.ui.auth.AuthLoading
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountState
import com.example.voxel_review.ui.theme.*
import com.example.voxel_review.ui.utils.Boton
import com.example.voxel_review.ui.utils.CampoContrasena
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun FomulariosCreateAcount(
    onBioChange: (String) -> Unit,
    state: CreateAccountState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    onTerminosAceptadosChange: () -> Unit,
    onUsernameChange: (String) -> Unit,
    unirseButtonPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // CAMPO USUARIO
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "GAMER TAG / USERNAME",
                color = VoxelTextSecondary,
                fontWeight = FontWeight.Bold
            )

            FormularioCreateAccount(
                value = state.username,
                enabled = !state.isLoading,
                isError = state.fieldErrors.containsKey("username"),
                icono = R.drawable.imagen_login_user,
                placeholder = "Nombre de usuario",
                onValueChange = onUsernameChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            AuthFieldError(state.fieldErrors["username"])
        }

        // CAMPO BIO
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Biografia",
                color = VoxelTextSecondary,
                fontWeight = FontWeight.Bold
            )

            FormularioCreateAccount(
                value = state.bio,
                enabled = !state.isLoading,
                isError = state.fieldErrors.containsKey("bio"),
                icono = R.drawable.bio,
                placeholder = "Biografia",
                onValueChange = onBioChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            AuthFieldError(state.fieldErrors["bio"])
        }

        // CAMPO CORREO
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "CORREO ELECTRÓNICO",
                color = VoxelTextSecondary,
                fontWeight = FontWeight.Bold
            )

            FormularioCreateAccount(
                value = state.email,
                keyboardType = androidx.compose.ui.text.input.KeyboardType.Email,
                enabled = !state.isLoading && !state.profilePending,
                isError = state.fieldErrors.containsKey("email"),
                icono = R.drawable.img_correo,
                placeholder = "Correo electrónico",
                onValueChange = onEmailChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            AuthFieldError(state.fieldErrors["email"])
        }

        // CAMPO CONTRASEÑA
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "CONTRASEÑA",
                color = VoxelTextSecondary,
                fontWeight = FontWeight.Bold
            )

            CampoContrasena(
                password = state.password,
                enabled = !state.isLoading && !state.profilePending,
                isError = state.fieldErrors.containsKey("password"),
                mostrarContrasena = state.mostrarContrasena,
                onPasswordChange = onPasswordChange,
                onMostrarContrasenaChange = onMostrarContrasenaChange,
                icono = if (state.mostrarContrasena) {
                    R.drawable.visible
                } else {
                    R.drawable.img_no_ver_contrasena
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }

        AuthFieldError(state.fieldErrors["password"])
        if (!state.profilePending) Text("Usa al menos 6 caracteres. Combina letras, números y símbolos.", color = VoxelTextSecondary, fontSize = 12.sp)
        if (state.profilePending) Text("Tu cuenta ya está creada. Completa o reintenta guardar tu perfil.", color = VoxelPrimary, fontSize = 13.sp)
        AuthFieldError(state.errorMessage)

        // TÉRMINOS Y PRIVACIDAD
        TerminosPrivacidad(
            aceptado = state.terminosAceptados,
            onAceptadoChange = {
                if (!state.isLoading) onTerminosAceptadosChange()
            }
        )

        AuthFieldError(state.fieldErrors["terms"])
        if (state.isLoading) AuthLoading()

        // BOTÓN REGISTRO
        Boton(
            textoBoton = when {
                state.isLoading -> "Guardando…"
                state.profilePending -> "Reintentar guardar perfil"
                else -> "Unirse a la aventura"
            },
            enabled = !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colorFondo = VoxelSecondary,
            colorBorde = VoxelSecondary,
            colorLetra = White,
            onClick = unirseButtonPressed
        )

        // INICIAR SESIÓN
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = VoxelTextSecondary)
                ) {
                    append("¿Ya tienes una cuenta?")
                }

                withStyle(
                    style = SpanStyle(color = VoxelPrimary)
                ) {
                    append(" Iniciar sesión")
                }
            },
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FomulariosCreateAcountPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FondoPantalla()

        FomulariosCreateAcount(
            onBioChange = {},
            state = CreateAccountState(),
            onUsernameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onMostrarContrasenaChange = {},
            onTerminosAceptadosChange = {},
            unirseButtonPressed = {},
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}
