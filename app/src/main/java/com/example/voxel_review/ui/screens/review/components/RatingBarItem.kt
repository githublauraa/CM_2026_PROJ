package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Indicador de progreso lineal con texto para desglose de aspectos de calificación (Jugabilidad, Gráficos, etc.).
 *
 * @param label Nombre de la categoría a evaluar.
 * @param value Valor numérico de la calificación actual.
 * @param maxValue Valor numérico máximo posible.
 * @param color Color para identificar la barra de progreso.
 * @param modifier Modificador para personalizar la barra.
 */
@Composable
fun RatingBarItem(
    label: String,
    value: Float,
    maxValue: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                androidx.compose.foundation.layout.Arrangement.SpaceBetween
        ) {

            Text(
                text = label,
                color = Color.LightGray,
                fontSize = 12.sp
            )

            Text(
                text = value.toString(),
                color = color,
                fontSize = 12.sp
            )
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        LinearProgressIndicator(
            progress = {
                value / maxValue
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(CircleShape),

            color = color,
            trackColor = Color.DarkGray.copy(alpha = 0.3f),
            strokeCap = StrokeCap.Round
        )
    }
}