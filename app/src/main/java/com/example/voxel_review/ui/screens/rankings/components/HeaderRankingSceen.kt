package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.utils.ScreenHeaderTitle

@Composable
fun HeaderRankingSceen(
    seleccionado: Boolean,
    onChangeSeleccionado: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 18.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        ScreenHeaderTitle("Rankings", modifier = Modifier.fillMaxWidth())
        Text(
            text = "Críticos por número de reseñas",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            color = onSurfaceVariantDark
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .background(VoxelSurface, RoundedCornerShape(16.dp))
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            listOf(true to "Críticos", false to "Juegos").forEach { (critics, label) ->
                val selected = seleccionado == critics
                TextButton(
                    onClick = { onChangeSeleccionado(critics) },
                    modifier = Modifier.weight(1f).semantics { this.selected = selected },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = if (selected) VoxelSurfaceVariant else VoxelSurface,
                        contentColor = if (selected) VoxelPrimary else onSurfaceVariantDark
                    )
                ) {
                    Text(label)
                }
            }
        }
    }
}
