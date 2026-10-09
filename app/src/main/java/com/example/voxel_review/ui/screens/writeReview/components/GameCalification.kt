
package com.example.voxel_review.ui.screens.writeReview.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.*

@Composable
fun GameCalification(
    gameplayRating: Int,
    graphicsRating: Int,
    storyRating: Int,
    onGameplayRatingChange: (Int) -> Unit,
    onGraphicsRatingChange: (Int) -> Unit,
    onStoryRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = VoxelSurface,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = VoxelSurfaceVariant,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(20.dp, 12.dp)
    ) {
        Text(
            text = "CALIFICACIONES",
            color = VoxelTextSecondary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        RatingSlider(
            title = "JUGABILIDAD",
            rating = gameplayRating,
            color = Color.Cyan,
            onRatingChange = onGameplayRatingChange
        )

        RatingSlider(
            title = "GRÁFICOS",
            rating = graphicsRating,
            color = Color.Magenta,
            onRatingChange = onGraphicsRatingChange
        )

        RatingSlider(
            title = "HISTORIA",
            rating = storyRating,
            color = Color.Yellow,
            onRatingChange = onStoryRatingChange
        )
    }
}

@Composable
fun RatingSlider(
    title: String,
    rating: Int,
    color: Color,
    onRatingChange: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                color = VoxelTextSecondary,
                fontSize = 12.sp
            )

            Text(
                text = rating.toString(),
                color = color,
                fontSize = 12.sp
            )
        }

        Slider(
            value = rating.toFloat(),
            onValueChange = {
                onRatingChange(it.toInt())
            },
            valueRange = 0f..5f,
            steps = 4,
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color
            )
        )
    }
}
