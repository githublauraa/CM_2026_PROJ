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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R


@Composable
fun CampoContrasena(
    modifier: Modifier = Modifier
){
    TextField(
        value = "",
        onValueChange = {},placeholder = {
            Text(
                text = stringResource(R.string.contrase_a),
                color = colorResource(R.color.Voxel_secundario)
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.Voxel_morado_oscuro),
            unfocusedContainerColor = colorResource(R.color.Voxel_morado_oscuro)
        ),
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.img_no_ver_contrasena),
                    contentDescription = stringResource(R.string.ocultar_contrase_a)
                )
            }
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.border(
            width = 2.dp,
            color = colorResource(R.color.Voxel_morado),
            shape = RoundedCornerShape(10.dp)
        )
    )
}