package com.example.voxel_review.ui.screens.writeReview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.White

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .background(VoxelBackground)
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp,
                ),
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterStart),
        ) {
            Icon(
                painter = painterResource(R.drawable.back_arrow),
                contentDescription = "Volver",
                tint = White,
                modifier = Modifier.size(24.dp),
            )
        }

        Text(
            text = "Escribir Reseña",
            color = White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center),
        )

        IconButton(
            onClick = {},
            modifier = Modifier.align(Alignment.CenterEnd),
        ) {
            Icon(
                painter = painterResource(R.drawable.settings),
                contentDescription = "Configuración",
                tint = White,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}
