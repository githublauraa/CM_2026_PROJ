package com.example.voxel_review.ui.screens.home.components

import androidx.compose.foundation.border
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun CampoUsuario(
    modifier: Modifier = Modifier
){
    TextField(
        value = "",
        onValueChange = {},
        placeholder = {
            Text(
                text = "tu_usuario",
                color = colorResource(R.color.Voxel_secundario)
            ) },//placeholder para que desaparezca apenas escribe el usuario
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.imagen_login_user),
                    contentDescription = "imagen para campo usuario"
                )
            }
        }, modifier = modifier.border(
            width = (2.dp),
            color = colorResource(R.color.Voxel_morado)
        )
    )
}