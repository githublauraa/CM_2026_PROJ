package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.voxel_review.ui.screens.crearCuenta.CreateAccountState
import com.example.voxel_review.ui.theme.errorDark
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.utils.AuthSecondaryButton
import com.example.voxel_review.ui.utils.AuthSubmitButton
import com.example.voxel_review.ui.utils.authFieldColors
import com.example.voxel_review.ui.utils.authFieldVisibility
import com.example.voxel_review.ui.utils.authFormBackground

@Composable
internal fun RegisterForm(
    state: CreateAccountState,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    onTerminosAceptadosChange: () -> Unit,
    onSubmit: () -> Unit,
    onBackToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = authFieldColors()
    val focusManager = LocalFocusManager.current
    val errorRequester = remember { BringIntoViewRequester() }
    LaunchedEffect(state.errorMessage) {
        if (state.errorMessage.isNotEmpty()) errorRequester.bringIntoView()
    }

    Column(
        modifier = modifier.authFormBackground(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.login_create_account),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = VoxelTextPrimary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        RegisterField(
            value = state.username,
            onValueChange = onUsernameChange,
            label = stringResource(R.string.register_username),
            icon = Icons.Outlined.Person,
            colors = colors,
            modifier = Modifier.testTag("txtNombre")
        )
        RegisterField(
            value = state.bio,
            onValueChange = onBioChange,
            label = stringResource(R.string.register_bio),
            icon = Icons.Outlined.Edit,
            colors = colors,
            modifier = Modifier.testTag("txtBio")
        )
        RegisterField(
            value = state.email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.login_email),
            icon = Icons.Outlined.Email,
            colors = colors,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.testTag("txtCorreo")
        )
        OutlinedTextField(
            value = state.password,
            onValueChange = onPasswordChange,
            label = { Text(stringResource(R.string.contrase_a)) },
            supportingText = {
                Text(stringResource(R.string.register_password_hint), color = onSurfaceVariantDark)
            },
            modifier = authFieldVisibility().fillMaxWidth().testTag("txtContrasenia"),
            shape = RoundedCornerShape(14.dp),
            colors = colors,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = if (state.mostrarContrasena) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
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
        TerminosPrivacidad(
            aceptado = state.terminosAceptados,
            onAceptadoChange = { onTerminosAceptadosChange() }
        )
        AuthSubmitButton(
            label = stringResource(R.string.login_create_account),
            loading = state.loading,
            onClick = {
                focusManager.clearFocus()
                onSubmit()
            },
            modifier = Modifier.testTag("btnRegistrarse")
        )
        AuthSecondaryButton(
            label = stringResource(R.string.register_back_to_login),
            onClick = onBackToLogin
        )
        Text(
            text = state.errorMessage,
            color = errorDark,
            minLines = 2,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .bringIntoViewRequester(errorRequester)
                .testTag("mensajeError")
                .semantics { liveRegion = LiveRegionMode.Polite }
        )
    }
}

@Composable
private fun RegisterField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    colors: TextFieldColors,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.then(authFieldVisibility()).fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = colors,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
        trailingIcon = { Icon(icon, contentDescription = null, modifier = Modifier.size(22.dp)) }
    )
}
