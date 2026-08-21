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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.onPrimaryDark
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.onSurfaceDark

@Composable
fun CampoUsuario(
    usuario: String,
    icono: Int,
    onUserChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = usuario,
        onValueChange = onUserChange,

        placeholder = {
            Text(
                text = stringResource(R.string.tu_usuario),
                color = onSurfaceVariantDark
            )
        },

        colors = TextFieldDefaults.colors(
            focusedContainerColor = onPrimaryDark,
            unfocusedContainerColor = onPrimaryDark,
            focusedTextColor = onSurfaceDark,
            unfocusedTextColor = onSurfaceDark
        ),

        trailingIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(icono),
                    contentDescription = stringResource(
                        R.string.imagen_para_campo_usuario
                    )
                )
            }
        },

        shape = RoundedCornerShape(10.dp),

        modifier = modifier.border(
            width = 2.dp,
            color = onPrimaryDark,
            shape = RoundedCornerShape(10.dp)
        )
    )
}

@Composable
@Preview
fun CampoUsuarioPreview() {
    CampoUsuario(
        usuario = "",
        icono = R.drawable.imagen_login_user,
        onUserChange = {}
    )
}