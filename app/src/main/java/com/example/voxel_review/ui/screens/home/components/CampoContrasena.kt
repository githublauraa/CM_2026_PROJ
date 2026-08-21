package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White

@Composable
fun CampoContrasena(
    password: String,
    mostrarContrasena: Boolean,
    icono: Int,
    onPasswordChange: (String) -> Unit,
    onMostrarContrasenaChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,

        placeholder = {
            Text(
                text = stringResource(R.string.contrase_a),
                color = VoxelTextSecondary
            )
        },

        visualTransformation =
            if (mostrarContrasena) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

        colors = TextFieldDefaults.colors(
            focusedContainerColor = VoxelSurfaceVariant,
            unfocusedContainerColor = VoxelSurfaceVariant,
            focusedTextColor = White,
            unfocusedTextColor = White
        ),

        trailingIcon = {
            IconButton(
                onClick = onMostrarContrasenaChange
            ) {
                Icon(
                    painter = painterResource(icono),
                    contentDescription = stringResource(R.string.ocultar_contrase_a)
                )
            }
        },

        shape = RoundedCornerShape(10.dp),

        modifier = modifier.border(
            width = 2.dp,
            color = VoxelSurfaceVariant,
            shape = RoundedCornerShape(10.dp)
        )
    )
}

@Composable
@Preview
fun CampoContrasenaPreview() {
    CampoContrasena(
        password = "",
        mostrarContrasena = false,
        icono = R.drawable.img_no_ver_contrasena,
        onPasswordChange = {},
        onMostrarContrasenaChange = {}
    )
}