package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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

        itemsIndexed(state.users.sortedByDescending { it.numResenias }) { posicion, usuario ->

            RankingCard(
                posicion = (posicion + 1).toString(),
                nombre = usuario.nick,
                reseñas = "${usuario.numResenias.toString()} reseñas",
                porcentaje = 0.toString(),
                imagen = usuario.pfp,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        }
    }
}