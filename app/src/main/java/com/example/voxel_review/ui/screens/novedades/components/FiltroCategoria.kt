package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FiltroCategoria(
    texto: String,
    seleccionado: Boolean = false,
    modifier: Modifier = Modifier
) {
    Text(
        text = texto,
        color = if (seleccionado) Color.Black else Color(0xFF9B93AF),
        fontSize = 14.sp,
        modifier = modifier
            .background(
                color = if (seleccionado) {
                    Color(0xFF10DDF5)
                } else {
                    Color(0xFF17122D)
                },
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = if (seleccionado) {
                    Color.Transparent
                } else {
                    Color(0xFF3A2D64)
                },
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = 17.dp,
                vertical = 8.dp
            )
    )
}