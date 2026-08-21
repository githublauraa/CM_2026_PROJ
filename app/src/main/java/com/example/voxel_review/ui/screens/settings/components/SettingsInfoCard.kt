package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun SettingsInfoCard() {
    SettingsCard {
        Text(
            text = "Términos de servicio y privacidad",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Voxel Review Versión 1.4.2 · Hecho por y para apasionados del gaming",
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                alpha = 0.55f
            ),
            fontSize = 11.sp,
            lineHeight = 15.sp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun SettingsInfoCardPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        SettingsInfoCard()
    }
}