package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.screens.rankings.RankingsState
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@Composable
fun LazyColumnRanking(
    state: RankingsState,
    onUserClick: (ProfileInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    val rankedUsers = remember(state.users) {
        state.users.sortedByDescending { it.numResenias }.withIndex().toList()
    }
    val query = state.searchQuery.trim()
    val results = remember(rankedUsers, query) {
        rankedUsers.filter { it.value.nick.contains(query, ignoreCase = true) }
    }
    if (results.isEmpty()) {
        Box(modifier.fillMaxSize().padding(24.dp), contentAlignment = Alignment.Center) {
            Text(
                text = if (query.isEmpty()) "Aún no hay críticos en el ranking"
                    else "No encontramos críticos con ese nombre",
                textAlign = TextAlign.Center,
                color = onSurfaceVariantDark,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        return
    }
    val showPodium = query.isEmpty() && results.size >= 3
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (showPodium) {
            item(key = "ranking-podium") {
                RankingPodium(results.take(3), onUserClick)
            }
            if (results.size > 3) {
                item(key = "ranking-standings") {
                    Text(
                        text = "Más críticos",
                        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = VoxelTextPrimary
                    )
                }
            }
        }
        items(if (showPodium) results.drop(3) else results, key = { it.value.id }) { (index, user) ->
            RankingCard(
                position = index + 1,
                user = user,
                onClick = { onUserClick(user) },
                modifier = Modifier.testTag("profile")
            )
        }
    }
}
