package com.example.voxel_review.ui.screens.settings

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelAccentPurple
import com.example.voxel_review.ui.theme.VoxelAccentYellow
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary

enum class AccentOption(val color: Color, @get:StringRes val label: Int) {
    CYAN(VoxelPrimary, R.string.settings_color_cyan),
    PINK(VoxelSecondary, R.string.settings_color_pink),
    AQUA(Color(0xFF10E7CD), R.string.settings_color_aqua),
    YELLOW(VoxelAccentYellow, R.string.settings_color_yellow),
    PURPLE(VoxelAccentPurple, R.string.settings_color_purple)
}
