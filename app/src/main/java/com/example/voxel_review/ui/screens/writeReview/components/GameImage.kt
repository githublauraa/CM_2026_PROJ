package com.example.voxel_review.ui.screens.writeReview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun GameImage(
    imagen: Int,
    descripcion: String,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(imagen),
        contentDescription = descripcion,
        modifier =
            modifier
                .width(88.dp)
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop,
    )
}
