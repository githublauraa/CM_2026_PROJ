package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun StarRating(
    rating: Int
) {
    Row {

        repeat(5) { index ->

            Icon(
                painter = painterResource(R.drawable.ic_star),
                contentDescription = null,

                tint = if (index < rating) {
                    colorResource(R.color.Voxel_amarillo)
                } else {
                    Color.Gray
                },

                modifier = Modifier.size(14.dp)
            )
        }
    }
}