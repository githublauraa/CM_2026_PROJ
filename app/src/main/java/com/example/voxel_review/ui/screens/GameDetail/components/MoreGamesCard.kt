package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.backgroundDark

/**
 * Tarjeta indicadora con contador para representar juegos adicionales en la lista de recomendados.
 */
@Composable
fun MoreGamesCard() {

    Box(
        modifier = Modifier
            .size(
                width = 72.dp,
                height = 72.dp
            )
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundDark)
            .border(
                1.dp,
                color = backgroundDark,
                RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = "+5",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun MoreGamesCardPreview() {
    MoreGamesCard()
}