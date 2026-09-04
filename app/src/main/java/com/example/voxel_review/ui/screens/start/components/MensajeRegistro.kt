package com.example.voxel_review.ui.screens.start.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelTextSecondary

/**
 * Muestra una etiqueta para identificar un campo del formulario.
 *
 * @param tipo Texto que identifica el campo.
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun MensajeUsuario(
    modifier: Modifier = Modifier,
    tipo: String
) {
    Text(
        text = tipo,
        color = VoxelTextSecondary,
        fontSize = 15.sp,
        modifier = modifier
    )
}

/**
 * Vista previa de la etiqueta de usuario.
 */
@Composable
@Preview
fun MensajeUsuarioPreview() {
    MensajeUsuario(
        tipo = "Usuario"
    )
}