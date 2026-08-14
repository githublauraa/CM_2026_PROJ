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
import androidx.compose.ui.tooling.preview.Preview
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
                text = stringResource(R.string.tu_usuario),
                color = colorResource(R.color.Voxel_secundario)
            ) },//placeholder para que desaparezca apenas escribe el usuario
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.Voxel_morado_oscuro),
            unfocusedContainerColor = colorResource(R.color.Voxel_morado_oscuro)
        ),
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.imagen_login_user),
                    contentDescription = stringResource(R.string.imagen_para_campo_usuario)
                )
            }
        },
        shape = RoundedCornerShape(10.dp), //para que quede redondeado el campo
        modifier = modifier.border(
            width = (2.dp),
            color = colorResource(R.color.Voxel_morado_oscuro),
            shape = RoundedCornerShape(10.dp)
        )
    )
}

@Composable
@Preview
fun CampoUsuarioPreview(){
    CampoUsuario()
}