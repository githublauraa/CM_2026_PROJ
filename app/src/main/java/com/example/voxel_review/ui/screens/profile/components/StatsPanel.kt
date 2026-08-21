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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White

@Composable
fun StatsPanel(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .background(
                color = VoxelSurface,
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 1.dp,
                color = VoxelBorder,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(
                vertical = 16.dp,
                horizontal = 10.dp
            )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Reseñas
            ItemEstadistica(
                valor = "47",
                etiqueta = "RESEÑAS",
                colorValor = VoxelPrimary,
                colorEtiqueta = VoxelTextSecondary,
                modifier = Modifier.weight(1f)
            )

            VerticalDivider(
                color = VoxelBorder,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight(0.8f)
            )

            // Promedio
            ItemEstadistica(
                valor = "4.3",
                etiqueta = "PROMEDIO",
                colorValor = VoxelSecondary,
                colorEtiqueta = VoxelTextSecondary,
                modifier = Modifier.weight(1f)
            )

            VerticalDivider(
                color = VoxelBorder,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight(0.8f)
            )

            // Likes
            ItemEstadistica(
                valor = "312",
                etiqueta = "LIKES",
                colorValor = White,
                colorEtiqueta = VoxelTextSecondary,
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
            fontSize = 24.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = etiqueta,
            color = colorEtiqueta,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}