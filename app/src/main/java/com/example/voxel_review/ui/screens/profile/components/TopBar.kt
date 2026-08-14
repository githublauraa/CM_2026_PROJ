package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    val colorFondo = colorResource(R.color.Voxel_negro)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colorFondo)
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Volver",
		tint = Color.White,
		modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "Mi Perfil",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )

        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Configuración",
		tint = Color.White,
		modifier = Modifier.size(24.dp)
            )
        }
    }
}
