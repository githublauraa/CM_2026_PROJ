package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.surfaceDark

/**
 * Selector de estado de colección personal (Por jugar, Jugando, Completado) en el detalle del juego.
 */
@Composable
fun LibraryStatusSelector(
    selectedStatus: LibraryStatus?,
    isUpdating: Boolean,
    errorMessage: String?,
    onSelectStatus: (LibraryStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "MI COLECCIÓN",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(end = 8.dp)
        ) {
            items(LibraryStatus.entries) { status ->
                val isSelected = selectedStatus == status
                val icon = when (status) {
                    LibraryStatus.WANT_TO_PLAY -> Icons.Filled.Bookmark
                    LibraryStatus.PLAYING -> Icons.Filled.SportsEsports
                    LibraryStatus.COMPLETED -> Icons.Filled.CheckCircle
                }
                val accentColor = when (status) {
                    LibraryStatus.WANT_TO_PLAY -> VoxelPrimary
                    LibraryStatus.PLAYING -> VoxelSecondary
                    LibraryStatus.COMPLETED -> VoxelPrimary
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(if (isSelected) accentColor else VoxelSurface)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) accentColor else VoxelBorder,
                            shape = RoundedCornerShape(50)
                        )
                        .clickable(enabled = !isUpdating) {
                            onSelectStatus(status)
                        }
                        .padding(horizontal = 14.dp, vertical = 9.dp)
                        .testTag("libraryChip_${status.name}")
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = status.label,
                        tint = if (isSelected) surfaceDark else VoxelTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = status.label,
                        color = if (isSelected) surfaceDark else Color.White,
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        if (!errorMessage.isNullOrBlank()) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = errorMessage,
                color = VoxelSecondary,
                fontSize = 12.sp
            )
        }
    }
}
