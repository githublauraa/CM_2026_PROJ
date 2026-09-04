package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.ui.theme.secondaryDark
import com.example.voxel_review.ui.theme.surfaceDark
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.surfaceDimDark

/**
 * Chip individual que representa una categoría o género de videojuego seleccionable.
 *
 * @param genre Información del género que se va a mostrar.
 * @param isSelected Indica si el chip se encuentra en estado seleccionado.
 * @param onClick Acción ejecutada al seleccionar o hacer clic en el chip.
 * @param modifier Modificador para personalizar la apariencia del chip.
 */
@Composable
fun GenreChip(
    genre: GenreInfo,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = genre.nombre),
        color = if (isSelected) surfaceDark else onSurfaceVariantDark,
        fontSize = 14.sp,
        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) secondaryDark else surfaceDimDark)
            .clickable { onClick() }
            .padding(horizontal = 18.dp, vertical = 10.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun GenreChipSelectedPreview() {
    GenreChip(
        genre = GenreInfo(id = "1", nombre = R.string.genero_todo),
        isSelected = true,
        onClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun GenreChipUnselectedPreview() {
    GenreChip(
        genre = GenreInfo(id = "2", nombre = R.string.genero_rpg),
        isSelected = false,
        onClick = {}
    )
}