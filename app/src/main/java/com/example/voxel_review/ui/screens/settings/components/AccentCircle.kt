package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.screens.settings.AccentOption
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun AccentCircle(
    option: AccentOption,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .then(
                if (selected) {
                    Modifier.border(
                        3.dp,
                        MaterialTheme.colorScheme.onSurface,
                        CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .padding(
                if (selected) 4.dp else 0.dp
            )
            .background(
                option.color,
                CircleShape
            )
            .clickable(
                onClick = onClick
            )
    )
}
@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
private fun AccentCirclePreview() {
    Voxel_ReviewTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        AccentCircle(
            option = AccentOption.PURPLE,
            selected = true,
            onClick = {}
        )
    }
}