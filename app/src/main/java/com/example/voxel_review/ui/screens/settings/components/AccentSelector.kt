package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.settings.AccentOption
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@Composable
fun AccentSelector(
    selectedAccent: AccentOption,
    onAccentChange: (AccentOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = selectedAccent.color
    val highlight = lerp(accent, VoxelTextPrimary, 0.35f)
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VoxelSurface,
        shape = RoundedCornerShape(22.dp),
        border = BorderStroke(1.dp, highlight.copy(alpha = 0.32f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(R.string.settings_panel_color),
                    style = MaterialTheme.typography.titleMedium,
                    color = VoxelTextPrimary
                )
                Text(
                    text = stringResource(selectedAccent.label),
                    style = MaterialTheme.typography.bodyMedium,
                    color = highlight
                )
            }

            Box(
                modifier = Modifier.fillMaxWidth().background(
                    VoxelSurface.copy(alpha = 0.68f), RoundedCornerShape(18.dp)
                ).padding(4.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth().selectableGroup()) {
                    AccentOption.entries.forEach { option ->
                        AccentCircle(
                            option = option,
                            selected = option == selectedAccent,
                            onClick = { onAccentChange(option) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
