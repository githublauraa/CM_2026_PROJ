package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun GenreFilterSection(
    genres: List<GenreInfo>,
    selectedGenre: GenreInfo,
    onGenreSelected: (GenreInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(genres) { genre ->
            GenreChip(
                genre = genre,
                isSelected = genre == selectedGenre,
                onClick = { onGenreSelected(genre) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GenreFilterSectionPreview() {
    GenreFilterSection(
        genres = listOf(
            GenreInfo("1", nombre = R.string.genero_todo),
            GenreInfo("2", nombre = R.string.genero_accion),
            GenreInfo("3", nombre = R.string.genero_aventura),
            GenreInfo("4", nombre = R.string.genero_rpg),
            GenreInfo("5", nombre = R.string.genero_estrategia),
            GenreInfo("6", nombre = R.string.genero_indie),
        ),
        selectedGenre = GenreInfo("0",nombre = R.string.genero_todo),
        onGenreSelected = {}
    )
}

