package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.rankings.RankingsState

/**
 * Muestra la lista de usuarios del ranking mediante una lista desplazable.
 * Cada elemento del estado se representa utilizando un RankingCard.
 *
 * @param state Estado que contiene la información de los usuarios del ranking.
 * @param modifier Modificador para personalizar la lista.
 */
@Composable
fun LazyColumnRanking(
    modifier: Modifier = Modifier,
    state: RankingsState
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 175.dp,
                start = 18.dp,
                end = 18.dp
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.rankingsUser) { usuario ->
            RankingCard(
                posicion = usuario.posicion,
                nombre = usuario.nombre,
                reseñas = usuario.reseñas,
                porcentaje = usuario.porcentaje
            )
        }
    }
}