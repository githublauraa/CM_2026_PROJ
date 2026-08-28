package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.screens.settings.AccentOption
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun AccentSelector(
    selectedAccent: AccentOption,
    onAccentChange: (AccentOption) -> Unit
) {
    SettingsCard(
        color = VoxelSurfaceVariant
    ) {
        Text(
            text = "Cambiar color de tema de la aplicación:",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 13.sp
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AccentOption.entries.forEach { option ->
                AccentCircle(
                    option = option,
                    selected = option == selectedAccent,
                    onClick = {
                        onAccentChange(option)
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun AccentSelectorPreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        AccentSelector(
            selectedAccent = AccentOption.CYAN,
            onAccentChange = {}
        )
    }
}