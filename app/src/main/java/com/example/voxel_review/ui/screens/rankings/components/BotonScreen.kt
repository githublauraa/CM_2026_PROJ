package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BotonScreen(
    textoBoton: String,
    seleccionado: Boolean,
    colorFondo: Color,
    colorSeleccionado: Color,
    colorTexto: Color,
    colorBorde: Color = colorFondo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (seleccionado) {
                colorSeleccionado
            } else {
                colorFondo
            }
        ),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(
            width = 1.dp,
            color = colorBorde
        ),
        modifier = modifier
            .width(100.dp)
            .height(33.dp)
    ) {
        Text(
            text = textoBoton,
            color = colorTexto
        )
    }
}