package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.screens.rankings.RankingsState

@Composable
fun LazyColumnRanking(
    state: RankingsState,
    onUserClick: (ProfileInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        contentPadding = PaddingValues(
            top = 4.dp,
            bottom = 90.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        itemsIndexed(state.filteredUsers.sortedByDescending { it.numResenias }) { posicion, usuario ->

            RankingCard(
                posicion = (posicion + 1).toString(),
                nombre = usuario.nick,
                reseñas = "${usuario.numResenias} reseñas",
                porcentaje = 0.toString(),
                imagen = usuario.pfp,
                onClick = { onUserClick(usuario) },
                modifier = Modifier.padding(vertical = 5.dp)
                    .testTag("profile")
            )
        }
    }
}
