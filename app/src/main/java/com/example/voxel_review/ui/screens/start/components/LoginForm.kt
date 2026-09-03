package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.voxel_review.ui.screens.start.StartState
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.example.voxel_review.ui.utils.CampoContrasena
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.surfaceContainerLowestLight

@Composable
fun LoginForm(
    state: StartState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    modifier: Modifier = Modifier
) {

    val icono =
        if (state.mostrarContrasena) {
            R.drawable.visible
        } else {
            R.drawable.img_no_ver_contrasena
        }

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
            onPasswordChange = onPasswordChange,
            mostrarContrasena = state.mostrarContrasena,
            onMostrarContrasenaChange = {
                onMostrarContrasenaChange()
            },
            modifier = Modifier
                .width(320.dp)
                .height(50.dp)
        )

        if (
            state.errorMessage.isNotEmpty()
        ) {
            Text(
                text = state.errorMessage,
                color = surfaceContainerLowestLight,
                fontSize = 10.sp
            )
        }
    }
}