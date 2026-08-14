package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun StatsPanel(modifier: Modifier = Modifier) {

    val colorFondo = colorResource(id = R.color.Voxel_elemento)
    val colorBorde = Color(0xFF2E2B5F)
    val colorTextoSecundario = colorResource(id = R.color.Voxel_secundario)

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .background(color = colorFondo, shape = RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = colorBorde, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 16.dp, horizontal = 10.dp)
    ) {
        // IntrinsicSize.Min es vital para que el VerticalDivider sepa qué altura tomar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), 
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // # Reseñas
            ItemEstadistica(
                valor = "47",
                etiqueta = "RESEÑAS",
                colorValor = colorResource(id = R.color.voxel_azul),
                colorEtiqueta = colorTextoSecundario,
                modifier = Modifier.weight(1f)
            )

            VerticalDivider(
                color = colorBorde,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight(0.8f) // Ocupa el 80% del alto disponible
            )

            // Promedio de notas
            ItemEstadistica(
                valor = "4.3",
                etiqueta = "PROMEDIO",
                colorValor = colorResource(id = R.color.Voxel_rosado),
                colorEtiqueta = colorTextoSecundario,
                modifier = Modifier.weight(1f)
            )

            VerticalDivider(
                color = colorBorde,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight(0.8f)
            )

            // Likes recibidos
            ItemEstadistica(
                valor = "312",
                etiqueta = "LIKES",
                colorValor = Color.White,
                colorEtiqueta = colorTextoSecundario,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ItemEstadistica(
    valor: String,
    etiqueta: String,
    colorValor: Color,
    colorEtiqueta: Color,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = valor,
            color = colorValor,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = etiqueta,
            color = colorEtiqueta,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}
