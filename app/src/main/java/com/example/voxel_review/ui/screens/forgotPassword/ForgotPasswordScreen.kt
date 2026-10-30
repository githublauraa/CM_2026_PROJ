package com.example.voxel_review.ui.screens.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.start.components.CampoUsuario
import com.example.voxel_review.ui.screens.start.components.MensajeUsuario
import com.example.voxel_review.ui.screens.start.components.NombreApp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.backgroundDark
import com.example.voxel_review.ui.theme.secondaryDark
import com.example.voxel_review.ui.theme.surfaceContainerLowestLight
import com.example.voxel_review.ui.theme.tertiaryDark
import com.example.voxel_review.ui.utils.Boton
import com.example.voxel_review.ui.utils.FondoPantalla
import com.example.voxel_review.ui.utils.MostrarLogo

/**
 * Contenido visual sin estado para la pantalla de recuperación de contraseña.
 *
 * @param state Estado actual de la pantalla de recuperación.
 * @param onEmailChange Callback cuando cambia el correo ingresado.
 * @param onSendClick Callback para enviar el correo de restablecimiento.
 * @param onBackToLoginClick Callback para regresar al inicio de sesión.
 * @param modifier Modificador para personalizar el contenedor.
 */
@Composable
fun ForgotPasswordContent(
    state: ForgotPasswordState,
    onEmailChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            MostrarLogo(
                modifier = Modifier.width(110.dp)
            )

            NombreApp()

            Text(
                text = "Recuperar",
                color = VoxelPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Contraseña",
                color = VoxelSecondary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Ingresa el correo electrónico de tu cuenta y te enviaremos un enlace para restablecer tu contraseña.",
                color = VoxelTextSecondary,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Column(
            modifier = Modifier.width(320.dp)
        ) {
            MensajeUsuario(
                tipo = "Correo electrónico"
            )

            Spacer(modifier = Modifier.height(8.dp))

            CampoUsuario(
                usuario = state.correo,
                onUserChange = onEmailChange,
                icono = R.drawable.img_correo,
                modifier = Modifier
                    .width(320.dp)
                    .height(50.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = VoxelSecondary
                    )
                }
            }

            if (state.errorMessage.isNotEmpty()) {
                Text(
                    text = state.errorMessage,
                    color = surfaceContainerLowestLight,
                    fontSize = 12.sp
                )
            }

            if (state.isEmailSent) {
                Text(
                    text = "¡Enlace enviado! Revisa tu bandeja de entrada o carpeta de spam para restablecer tu contraseña.",
                    color = VoxelSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Boton(
                textoBoton = "Enviar enlace",
                colorFondo = tertiaryDark,
                colorBorde = tertiaryDark,
                colorLetra = surfaceContainerLowestLight,
                onClick = onSendClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            Boton(
                textoBoton = "Volver al inicio de sesión",
                colorFondo = backgroundDark,
                colorBorde = secondaryDark,
                colorLetra = secondaryDark,
                onClick = onBackToLoginClick
            )
        }
    }
}

/**
 * Pantalla de recuperación de contraseña conectada al ViewModel.
 *
 * @param forgotPasswordViewModel ViewModel encargado del estado y envío del correo de recuperación.
 * @param onBackToLoginClick Callback para regresar a la pantalla de inicio de sesión.
 * @param modifier Modificador para personalizar la pantalla.
 */
@Composable
fun ForgotPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel,
    onBackToLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by forgotPasswordViewModel.uiState.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla()

        ForgotPasswordContent(
            state = state,
            onEmailChange = forgotPasswordViewModel::updateCorreo,
            onSendClick = forgotPasswordViewModel::sendResetEmail,
            onBackToLoginClick = onBackToLoginClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FondoPantalla()

        ForgotPasswordContent(
            state = ForgotPasswordState(),
            onEmailChange = {},
            onSendClick = {},
            onBackToLoginClick = {}
        )
    }
}
