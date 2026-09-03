package com.example.voxel_review.ui.screens.settings

import androidx.compose.ui.graphics.Color
import com.example.voxel_review.ui.theme.VoxelAccentPurple
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary

enum class AccentOption(
    val color: Color
) {
    CYAN(VoxelPrimary),
    PINK(VoxelSecondary),
    AQUA(Color(0xFF10E7CD)),
    YELLOW(VoxelAccentYellow),
    PURPLE(VoxelAccentPurple)
}