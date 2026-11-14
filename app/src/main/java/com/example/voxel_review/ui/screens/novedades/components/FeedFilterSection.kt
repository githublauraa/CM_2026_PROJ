package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun FeedFilterSection(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit,
    onMapClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            onClick = onMapClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = VoxelSurfaceVariant,
            contentColor = VoxelTextPrimary
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Outlined.Place, contentDescription = null, tint = VoxelPrimary)
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Mapa de reseñas", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "Explora reseñas por ubicación",
                        style = MaterialTheme.typography.bodySmall,
                        color = onSurfaceVariantDark
                    )
                }
                Icon(Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = null, tint = VoxelPrimary)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(VoxelSurface, RoundedCornerShape(16.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            FeedFilterChip(
                text = "Todos",
                isSelected = selectedFilter == "Todos",
                onClick = { onFilterSelected("Todos") },
                modifier = Modifier.weight(1f)
            )
            FeedFilterChip(
                text = "Siguiendo",
                isSelected = selectedFilter == "Siguiendo",
                onClick = { onFilterSelected("Siguiendo") },
                modifier = Modifier.weight(1f).testTag("btnSiguiendo")
            )
        }
    }
}
