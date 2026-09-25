package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.rankings.RankingsState

@Composable
fun LazyColumnRanking(
    state: RankingsState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 180.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {

        items(state.rankingsUser) { usuario ->

            RankingCard(
                posicion = usuario.posicion,
                nombre = usuario.nombre,
                reseñas = usuario.reseñas,
                porcentaje = usuario.porcentaje,
                imagen = usuario.imagen,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}