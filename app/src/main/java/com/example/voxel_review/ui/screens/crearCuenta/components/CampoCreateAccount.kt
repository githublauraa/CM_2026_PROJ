package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.onPrimaryDark
import com.example.voxel_review.ui.theme.onSurfaceDark

@Composable
fun FormularioCreateAccount(
    value: String,
    icono: Int,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,

        placeholder = {
            Text(
                text = placeholder,
                color = VoxelTextSecondary
            )
        },

        colors = TextFieldDefaults.colors(
            focusedContainerColor = onPrimaryDark,
            unfocusedContainerColor = onPrimaryDark,
            focusedTextColor = onSurfaceDark,
            unfocusedTextColor = onSurfaceDark,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),

        modifier = modifier,

        trailingIcon = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = icono),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        },

        shape = RoundedCornerShape(15.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun FormularioCreateAccountPreview() {
    FormularioCreateAccount(
        value = "",
        icono = R.drawable.img_username,
        placeholder = "username",
        onValueChange = {}
    )
}