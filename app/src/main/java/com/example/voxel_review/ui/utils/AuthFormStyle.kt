package com.example.voxel_review.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
internal fun authFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedContainerColor = VoxelSurfaceVariant,
    unfocusedContainerColor = VoxelSurfaceVariant,
    focusedTextColor = VoxelTextPrimary,
    unfocusedTextColor = VoxelTextPrimary,
    focusedBorderColor = VoxelPrimary,
    unfocusedBorderColor = VoxelBorder,
    focusedLabelColor = VoxelPrimary,
    unfocusedLabelColor = onSurfaceVariantDark,
    focusedTrailingIconColor = VoxelPrimary,
    unfocusedTrailingIconColor = onSurfaceVariantDark,
    cursorColor = VoxelPrimary
)

internal fun Modifier.authFormBackground() = this
    .fillMaxWidth()
    .background(
        brush = Brush.verticalGradient(
            0f to VoxelSurface,
            0.55f to VoxelSurface.copy(alpha = 0.65f),
            1f to Color.Transparent
        ),
        shape = RoundedCornerShape(26.dp)
    )
    .drawBehind {
        val strokeWidth = 1.dp.toPx()
        val top = 26.dp.toPx()
        val bottom = size.height - top
        val accents = listOf(
            strokeWidth / 2 to VoxelSecondary.copy(alpha = 0.64f),
            (size.width - strokeWidth / 2) to VoxelPrimary.copy(alpha = 0.50f)
        )
        for ((x, color) in accents) {
            drawLine(
                brush = Brush.verticalGradient(
                    0f to Color.Transparent,
                    0.28f to color,
                    1f to Color.Transparent,
                    startY = top,
                    endY = bottom
                ),
                start = Offset(x, top),
                end = Offset(x, bottom),
                strokeWidth = strokeWidth
            )
        }
    }
    .padding(horizontal = 20.dp, vertical = 18.dp)
