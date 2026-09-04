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

/**
 * Campo de texto reutilizable para el formulario de creación de cuenta.
 *
 * @param value Texto actual del campo.
 * @param icono Recurso drawable que se muestra al final del campo.
 * @param placeholder Texto mostrado cuando el campo está vacío.
 * @param onValueChange Acción ejecutada cuando el usuario modifica el texto.
 * @param modifier Modificador para personalizar el componente.
 */
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

        // Personaliza los colores y elimina el indicador inferior del TextField.
        colors = TextFieldDefaults.colors(
            focusedContainerColor = onPrimaryDark,
            unfocusedContainerColor = onPrimaryDark,
            focusedTextColor = onSurfaceDark,
            unfocusedTextColor = onSurfaceDark,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),

        modifier = modifier,

        // Muestra el icono correspondiente al tipo de campo.
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

/**
 * Vista previa del campo del formulario.
 */
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