package com.example.voxel_review.ui.screens.chat.conversation.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelBackgroundDark
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant

@Composable
fun ChatBackdrop(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        drawRect(
            Brush.verticalGradient(
                listOf(VoxelSurfaceVariant.copy(alpha = 0.55f), VoxelBackgroundDark)
            )
        )

        val dotSpacing = 24.dp.toPx()
        val dotColor = VoxelSurfaceVariant.copy(alpha = 0.8f)
        val radius = 1.dp.toPx()
        var y = dotSpacing / 2
        while (y < size.height) {
            var x = dotSpacing / 2
            while (x < size.width) {
                drawCircle(dotColor, radius, Offset(x, y))
                x += dotSpacing
            }
            y += dotSpacing
        }
    }
}
