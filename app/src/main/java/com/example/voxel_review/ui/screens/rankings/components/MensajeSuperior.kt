package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.White

/**
 * Muestra el título principal de la pantalla de rankings.
 *
 * @param modifier Modificador para personalizar el componente.
 */
@Composable
fun MensajeSuperior(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Rankings usuarios",
        fontWeight = FontWeight.Bold,
        color = White,
        fontSize = 22.sp,
        modifier = modifier.padding(18.dp)
    )
}

/**
 * Vista previa del título de la pantalla de rankings.
 */
@Preview
@Composable
fun MensajeSuperiorPreview() {
    MensajeSuperior()
}