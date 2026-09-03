package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.surfaceDimDark


@Composable
fun GenreChip(
    genre: GenreInfo
) {

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(surfaceDimDark)
            .border(
                width = 1.dp,
                color = surfaceDimDark,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(
                horizontal = 14.dp,
                vertical = 6.dp
            )
    ) {

        Text(
            text = stringResource(id = genre.nombre),
            color = onSurfaceVariantDark,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
fun GenreChipPreview() {
    GenreChip(
        genre = LocalGenreProvider.generos.first { it.id == "1" }
    )
}