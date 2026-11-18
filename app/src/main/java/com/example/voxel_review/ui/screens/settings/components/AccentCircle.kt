package com.example.voxel_review.ui.screens.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.settings.AccentOption
import com.example.voxel_review.ui.theme.VoxelBackgroundDark
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@Composable
fun AccentCircle(
    option: AccentOption,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val label = stringResource(option.label)
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(CircleShape)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.size(42.dp).border(
                width = 1.dp,
                color = if (selected) VoxelTextPrimary else Color.Transparent,
                shape = CircleShape
            ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.size(34.dp).background(option.color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (selected) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = if (option.color.luminance() > 0.25f) VoxelBackgroundDark else VoxelTextPrimary,
                        modifier = Modifier.size(19.dp)
                    )
                }
            }
        }
    }
}
