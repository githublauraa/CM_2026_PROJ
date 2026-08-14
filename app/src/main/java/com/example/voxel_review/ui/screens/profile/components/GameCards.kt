package com.example.voxel_review.ui.screens.profile.components
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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

@Composable
fun GameCards(modifier: Modifier = Modifier) {
    val colorFondoGeneral = Color(0xFF0B0914)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colorFondoGeneral)
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Juegos",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
             Card(colorFondo = Color(0xFF1A3B66))
             Card(colorFondo = Color(0xFF9E471A))
             Card(colorFondo = Color(0xFF2E2B5F)) 
        }
    }
}

@Composable
fun Card(
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
                color = Color(0xFF2E2B5F), // Borde estático simple
                shape = RoundedCornerShape(16.dp)
            )
    )
}
