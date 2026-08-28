package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun SectionTitle(
    text: String
) {
    Text(
        text = text,
        color = VoxelTextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun SectionTitlePreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SectionTitle(
            text = "AJUSTES GENERALES"
        )
    }
}