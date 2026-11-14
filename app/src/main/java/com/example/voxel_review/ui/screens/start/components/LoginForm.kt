package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.start.StartState
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.errorDark
import com.example.voxel_review.ui.utils.authFieldColors
import com.example.voxel_review.ui.utils.authFieldVisibility
import com.example.voxel_review.ui.utils.authFormBackground

@Composable
fun LoginForm(
    state: StartState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSubmit: () -> Unit,
    onCreateAccount: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val fieldShape = RoundedCornerShape(14.dp)
    val fieldColors = authFieldColors()

    Column(
        modifier = modifier.authFormBackground(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.login_sign_in),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = VoxelTextPrimary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = state.correo,
            onValueChange = onUserNameChange,
            modifier = authFieldVisibility().fillMaxWidth().testTag("txtEmail"),
            label = { Text(stringResource(R.string.login_email)) },
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            modifier = authFieldVisibility().fillMaxWidth().testTag("txtPassword"),
            label = { Text(stringResource(R.string.contrase_a)) },
            singleLine = true,
            shape = fieldShape,
            colors = fieldColors,
            visualTransformation = if (state.mostrarContrasena) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onSubmit() }),
            trailingIcon = {
                IconButton(onClick = onMostrarContrasenaChange) {
                    Icon(
                        painter = painterResource(
                            if (state.mostrarContrasena) R.drawable.visible
                            else R.drawable.img_no_ver_contrasena
                        ),
                        contentDescription = stringResource(
                            if (state.mostrarContrasena) R.string.ocultar_contrase_a
                            else R.string.login_show_password
                        ),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        )
        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.textButtonColors(contentColor = VoxelPrimary)
        ) {
            Text(stringResource(R.string.login_forgot_password))
        }
        LoginButtons(
            logginButtonPressed = onSubmit,
            createAccountButtonPressed = onCreateAccount,
            loading = state.loading
        )
        // Reserve space for errors below the actions so the fields and buttons stay in place.
        Text(
            text = state.errorMessage,
            modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite },
            minLines = 2,
            style = MaterialTheme.typography.bodySmall,
            color = errorDark
        )
    }
}
