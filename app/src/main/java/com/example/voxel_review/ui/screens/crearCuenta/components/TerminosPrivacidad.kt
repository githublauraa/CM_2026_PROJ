package com.example.voxel_review.ui.screens.crearCuenta.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary

@Composable
fun TerminosPrivacidad(
    aceptado: Boolean,
    onAceptadoChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        Checkbox(
            checked = aceptado,
            onCheckedChange = onAceptadoChange,
            colors = CheckboxDefaults.colors(
                checkedColor = VoxelPrimary,
                uncheckedColor = VoxelPrimary,
                checkmarkColor = Color.Black
            )
        )

        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = VoxelTextSecondary
                    )
                ) {
                    append("Acepto los ")
                }

                withStyle(
                    style = SpanStyle(
                        color = VoxelPrimary,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("Términos de Servicio")
                }

                withStyle(
                    style = SpanStyle(
                        color = VoxelTextSecondary
                    )
                ) {
                    append(" y Privacidad")
                }
            },
            fontSize = 10.sp
        )
    }
}

@Composable
fun TerminosPrivacidadPreview() {
    TerminosPrivacidad(
        aceptado = true,
        onAceptadoChange = {}
    )
}