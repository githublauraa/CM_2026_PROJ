package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun FeedFilterSection(
    showOnlyFollowed: Boolean,
    onFilterSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        item {
            FeedFilterChip(
                text = "Todos los juegos",
                isSelected = !showOnlyFollowed,
                onClick = { onFilterSelected(false) }
            )
        }
        item {
            FeedFilterChip(
                text = "Siguiendo",
                isSelected = showOnlyFollowed,
                onClick = { onFilterSelected(true) }
            )
        }
    }
}