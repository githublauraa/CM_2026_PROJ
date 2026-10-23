package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun SettingsCard(
    color: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                com.example.voxel_review.ui.theme.VoxelBorder.copy(alpha = 0.6f),
                RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = com.example.voxel_review.ui.theme.VoxelSurface
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            content = content
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun SettingsCardPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SettingsCard(
            color = VoxelSurfaceVariant
        ) {
            Text(
                text = "Ejemplo de SettingsCard",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}