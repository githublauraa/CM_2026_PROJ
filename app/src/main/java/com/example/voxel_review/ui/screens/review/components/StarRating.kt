package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import androidx.compose.ui.graphics.Color.Companion.Gray

/**
 * Indicador visual compuesto por 5 estrellas para representar una calificación cuantitativa.
 *
 * @param rating Cantidad de estrellas rellenas a renderizar (0 a 5).
 */
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
                    VoxelAccentYellow
                } else {
                    Gray
                },

                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
@Preview
fun StarRatingPreview () {
    StarRating(
        rating = 5
    )
}