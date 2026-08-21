package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.border
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
@Composable
fun BotonScreen(
    textoBoton: String,
    colorFondo: Color,
    colorTexto: Color,
    colorBorde: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo
        ),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(1.dp, colorBorde),
        modifier = modifier.width(100.dp).height(33.dp),

    ) {
        Text(
            text = textoBoton,
            color = colorTexto
        )

    }
}


@Preview(showBackground = true)
@Composable
fun BotonScreenPreview() {
    BotonScreen(
        textoBoton = "Boton",
        colorFondo = Color.Black,
        colorTexto = Color.White,
        colorBorde = Color.Gray,
        onClick = {}
    )
}
