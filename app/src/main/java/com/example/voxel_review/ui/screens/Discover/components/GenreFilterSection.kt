package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun GenreFilterSection(
    genres: List<String>,
    selectedGenre: String,
    onGenreSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "GÉNEROS POPULARES",
            color = onSurfaceVariantDark,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(genres) { genre ->
                GenreChip(
                    label = genre,
                    isSelected = genre == selectedGenre,
                    onClick = { onGenreSelected(genre) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreFilterSectionPreview() {
    GenreFilterSection(
        genres = listOf("Todo", "RPG", "Acción", "Aventura", "Estrategia", "Indie"),
        selectedGenre = "Todo",
        onGenreSelected = {}
    )
}

