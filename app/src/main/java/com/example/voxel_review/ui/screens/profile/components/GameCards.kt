package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelGameBlue
import com.example.voxel_review.ui.theme.VoxelGameOrange
import com.example.voxel_review.ui.theme.White

@Composable
fun GameCards(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Mis Juegos",
            color = White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            GameCard(
                colorFondo = VoxelGameBlue
            )

            GameCard(
                colorFondo = VoxelGameOrange
            )

            GameCard(
                colorFondo = VoxelBorder
            )
        }
    }
}

@Composable
fun GameCard(
    colorFondo: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorFondo)
            .border(
                width = 1.dp,
                color = VoxelBorder,
                shape = RoundedCornerShape(16.dp)
            )
    )
}