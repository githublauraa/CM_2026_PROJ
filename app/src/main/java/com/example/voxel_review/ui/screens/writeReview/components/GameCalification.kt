package com.example.voxel_review.ui.screens.writeReview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.*

@Composable
fun GameCalification(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .height(145.dp)
                .background(
                    color = VoxelSurface,
                    shape = RoundedCornerShape(18.dp),
                ).border(
                    width = 1.dp,
                    color = VoxelSurfaceVariant,
                    shape = RoundedCornerShape(18.dp),
                ).padding(20.dp, 12.dp),
    ) {
        Text(
            text = "CALIFICACIONES",
            color = VoxelTextSecondary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )
        Image(
            painter = painterResource(id = R.drawable.rv_grades),
            contentDescription = "Calificaciones",
            modifier =
                Modifier
                    .size(250.dp)
                    .padding(top = 8.dp),
        )
    }
}
