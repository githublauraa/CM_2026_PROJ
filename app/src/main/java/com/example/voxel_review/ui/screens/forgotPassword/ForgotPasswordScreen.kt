package com.example.voxel_review.ui.screens.forgotPassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.start.components.StartHeader
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.errorDark
import com.example.voxel_review.ui.utils.AuthScreenLayout
import com.example.voxel_review.ui.utils.AuthSecondaryButton
import com.example.voxel_review.ui.utils.AuthSubmitButton
import com.example.voxel_review.ui.utils.authFieldColors
import com.example.voxel_review.ui.utils.authFieldVisibility
import com.example.voxel_review.ui.utils.authFormBackground

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
    val focusManager = LocalFocusManager.current
    val submit = {
        if (!state.isLoading) {
            focusManager.clearFocus()
            onSendClick()
        }
    }

    AuthScreenLayout(modifier) {
        StartHeader()

        Column(
            modifier = Modifier.authFormBackground(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(R.string.reset_password_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = VoxelTextPrimary
            )
            Text(
                text = stringResource(R.string.reset_password_description),
                style = MaterialTheme.typography.bodySmall,
                color = VoxelTextSecondary
            )
            OutlinedTextField(
                value = state.correo,
                onValueChange = onEmailChange,
                modifier = authFieldVisibility().fillMaxWidth(),
                label = { Text(stringResource(R.string.login_email)) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = authFieldColors(),
                trailingIcon = {
                    Icon(Icons.Outlined.Email, contentDescription = null, modifier = Modifier.size(22.dp))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { submit() })
            )
            AuthSubmitButton(
                label = stringResource(R.string.reset_password_send),
                loading = state.isLoading,
                onClick = submit
            )
            AuthSecondaryButton(
                label = stringResource(R.string.reset_password_back),
                onClick = onBackToLoginClick
            )

            val message = when {
                state.errorMessage.isNotEmpty() -> state.errorMessage
                state.isEmailSent -> stringResource(R.string.reset_password_sent)
                else -> ""
            }
            if (message.isNotEmpty()) {
                Text(
                    text = message,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    style = MaterialTheme.typography.bodySmall,
                    color = if (state.errorMessage.isNotEmpty()) errorDark else VoxelPrimary
                )
            }
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

    ForgotPasswordContent(
        state = state,
        onEmailChange = forgotPasswordViewModel::updateCorreo,
        onSendClick = forgotPasswordViewModel::sendResetEmail,
        onBackToLoginClick = onBackToLoginClick,
        modifier = modifier
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordContent(
        state = ForgotPasswordState(),
        onEmailChange = {},
        onSendClick = {},
        onBackToLoginClick = {}
    )
}
