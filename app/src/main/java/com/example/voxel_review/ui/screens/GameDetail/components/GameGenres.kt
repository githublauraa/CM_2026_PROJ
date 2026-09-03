package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider

@Composable
fun GameGenres(
    genres: List<GenreInfo>
) {

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        genres.forEach { genre ->

            GenreChip(
                genre = genre
            )
        }
    }
}

@Preview
@Composable
fun GameGenresPreview() {
    val genres = listOf(
        LocalGenreProvider.generos.first { it.id == "1" },
        LocalGenreProvider.generos.first { it.id == "2" },
        LocalGenreProvider.generos.first { it.id == "3" },
    )

    GameGenres(
        genres = genres
    )
}