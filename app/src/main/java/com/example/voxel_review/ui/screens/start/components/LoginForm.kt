package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.start.StartState
import com.example.voxel_review.ui.theme.surfaceContainerLowestLight
import com.example.voxel_review.ui.utils.CampoContrasena

/**
 * Formulario de inicio de sesión.
 * Muestra los campos de usuario y contraseña utilizando el estado recibido.
 *
 * @param state Estado actual de la pantalla de inicio.
 * @param onUserNameChange Acción ejecutada cuando cambia el nombre de usuario.
 * @param onPasswordChange Acción ejecutada cuando cambia la contraseña.
 * @param onMostrarContrasenaChange Acción para mostrar u ocultar la contraseña.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun LoginForm(
    state: StartState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MensajeLogin()

        Spacer(
            modifier = Modifier.height(13.dp)
        )

        MensajeUsuario(
            tipo = stringResource(R.string.usuario)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        CampoUsuario(
            usuario = state.usuario,
            onUserChange = onUserNameChange,
            icono = R.drawable.imagen_login_user,
            modifier = Modifier
                .width(320.dp)
                .height(50.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        MensajeUsuario(
            tipo = stringResource(R.string.contrase_aa)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        CampoContrasena(
            password = state.password,
            mostrarContrasena = state.mostrarContrasena,
            onPasswordChange = onPasswordChange,
            onMostrarContrasenaChange = onMostrarContrasenaChange,
            icono = if (state.mostrarContrasena) {
                R.drawable.visible
            } else {
                R.drawable.img_no_ver_contrasena
            },
            modifier = Modifier
                .width(330.dp)
                .height(50.dp)
        )

        // Muestra el mensaje generado durante la validación, si existe.
        if (state.errorMessage.isNotEmpty()) {
            Text(
                text = state.errorMessage,
                color = surfaceContainerLowestLight,
                fontSize = 10.sp
            )
        }
    }
}