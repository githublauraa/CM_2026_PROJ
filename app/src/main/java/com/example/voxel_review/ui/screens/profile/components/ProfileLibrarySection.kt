package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.voxel_review.R
import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.ui.screens.novedades.components.FeedFilterChip
import com.example.voxel_review.ui.screens.profile.ProfileContentTab
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.ContentStatus

/**
 * Fila de chips principales en el perfil para alternar entre Reseñas y Colección.
 */
@Composable
fun ProfileTabChips(
    selectedTab: ProfileContentTab,
    onSelectTab: (ProfileContentTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        ProfileContentTab.entries.forEach { tab ->
            FeedFilterChip(
                text = tab.label,
                isSelected = selectedTab == tab,
                onClick = { onSelectTab(tab) },
                modifier = Modifier.testTag("profileTab_${tab.name}")
            )
        }
    }
}

/**
 * Sección de la colección personal (Por jugar / Jugando / Completado) en el perfil del usuario.
 */
@Composable
fun ProfileLibrarySection(
    games: List<LibraryGameInfo>,
    selectedFilter: LibraryStatus?,
    isLoading: Boolean,
    errorMessage: String?,
    onSelectFilter: (LibraryStatus?) -> Unit,
    onGameClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Mi Colección",
            color = White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 14.dp)
        ) {
            item {
                FeedFilterChip(
                    text = "Todos",
                    isSelected = selectedFilter == null,
                    onClick = { onSelectFilter(null) },
                    modifier = Modifier.testTag("libraryFilter_ALL")
                )
            }
            items(LibraryStatus.entries) { status ->
                FeedFilterChip(
                    text = status.label,
                    isSelected = selectedFilter == status,
                    onClick = { onSelectFilter(status) },
                    modifier = Modifier.testTag("libraryFilter_${status.name}")
                )
            }
        }

        when {
            isLoading -> {
                ContentStatus("Cargando colección…", loading = true)
            }

            errorMessage != null -> {
                ContentStatus(errorMessage, onRetry = onRetry)
            }

            games.isEmpty() -> {
                val emptyText = if (selectedFilter == null) {
                    "Todavía no hay juegos en esta colección."
                } else {
                    "No hay juegos marcados como \"${selectedFilter.label}\"."
                }
                ContentStatus(emptyText)
            }

            else -> {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    games.forEach { entry ->
                        LibraryGameCard(
                            entry = entry,
                            onClick = { onGameClick(entry.videoGameId) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LibraryGameCard(
    entry: LibraryGameInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val badgeColor = when (entry.status) {
        LibraryStatus.WANT_TO_PLAY -> VoxelPrimary
        LibraryStatus.PLAYING -> VoxelSecondary
        LibraryStatus.COMPLETED -> VoxelPrimary
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, VoxelBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("libraryGame_${entry.videoGameId}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = VoxelSurface)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(entry.gameImageUrl.takeIf { it.isNotBlank() })
                    .crossfade(true)
                    .build(),
                contentDescription = entry.gameName,
                fallback = painterResource(id = R.drawable.imagen_login_user),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.imagen_login_user),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.gameName,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (entry.gameDeveloper.isNotBlank()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = entry.gameDeveloper,
                        color = VoxelTextSecondary,
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = entry.status.label,
                    color = badgeColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .background(Color.Transparent)
                        .border(1.dp, badgeColor, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
        }
    }
}
