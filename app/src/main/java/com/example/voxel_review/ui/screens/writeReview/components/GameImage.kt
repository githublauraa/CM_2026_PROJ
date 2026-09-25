package com.example.voxel_review.ui.screens.writeReview.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
fun GameImage(
    imagen: String,
    descripcion: String,
    modifier: Modifier = Modifier,
) {
    ProfileImage(
        profileImage = imagen,
        size = 88,
        modifier = modifier
            .width(88.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
    )
}