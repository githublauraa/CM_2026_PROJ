package com.example.voxel_review.ui.screens.following

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.utils.ContentStatus
import com.example.voxel_review.ui.utils.ProfileImage
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun FollowingScreen(
    userId: String,
    followingViewModel: FollowingViewModel,
    onBackClick: () -> Unit,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    showFollowers: Boolean = false
) {
    val state by followingViewModel.uiState.collectAsState()
    var selectedFollowers by rememberSaveable(userId, showFollowers) { mutableStateOf(showFollowers) }
    LaunchedEffect(userId, selectedFollowers) {
        followingViewModel.loadUsers(userId, selectedFollowers)
    }
    FollowingContent(
        state = if (state.showingFollowers == selectedFollowers) state else FollowingState(),
        showFollowers = selectedFollowers,
        onTabSelected = { selectedFollowers = it },
        onBackClick = onBackClick,
        onUserClick = onUserClick,
        onRetry = { followingViewModel.loadUsers(userId, selectedFollowers) },
        modifier = modifier
    )
}

@Composable
fun FollowingContent(
    state: FollowingState,
    onBackClick: () -> Unit,
    onUserClick: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    showFollowers: Boolean = false,
    onTabSelected: (Boolean) -> Unit = {}
) {
    var query by rememberSaveable(showFollowers) { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize().imePadding(),
        containerColor = VoxelBackground,
        topBar = { ScreenHeader("Conexiones", onBackClick) }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .fillMaxWidth()
                    .background(VoxelSurface, RoundedCornerShape(18.dp))
                    .selectableGroup()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ConnectionTab("Seguidores", showFollowers, { onTabSelected(true) }, Modifier.weight(1f))
                ConnectionTab("Seguidos", !showFollowers, { onTabSelected(false) }, Modifier.weight(1f))
            }

            when {
                state.isLoading -> Column {
                    ContentStatus(
                        if (showFollowers) "Cargando seguidores…" else "Cargando personas seguidas…",
                        loading = true
                    )
                }
                state.error != null -> Column {
                    ContentStatus(state.error, onRetry = onRetry)
                }
                state.users.isEmpty() -> Column {
                    ContentStatus(
                        if (showFollowers) "Este usuario todavía no tiene seguidores"
                        else "Este usuario todavía no sigue a nadie"
                    )
                }
                else -> {
                    val search = query.trim().removePrefix("@")
                    val users = state.users.filter { it.nick.contains(search, ignoreCase = true) }
                    Column(Modifier.weight(1f)) {
                        DiscoverSearchBar(
                            query = query,
                            onQueryChange = { query = it },
                            placeholder = "Buscar por nombre de usuario"
                        )
                        val count = state.users.size
                        val summary = if (search.isNotEmpty()) {
                            "${users.size} de $count perfiles"
                        } else if (showFollowers) {
                            if (count == 1) "1 seguidor" else "$count seguidores"
                        } else {
                            if (count == 1) "1 perfil seguido" else "$count perfiles seguidos"
                        }
                        Text(
                            text = summary,
                            color = VoxelTextSecondary,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.padding(start = 20.dp, top = 16.dp, end = 20.dp)
                        )
                        if (users.isEmpty()) {
                            ContentStatus("No se encontraron perfiles con ese nombre.")
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(users, key = { it.id }) { user ->
                                    FollowingUserCard(user = user, onClick = { onUserClick(user.id) })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ConnectionTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(14.dp)
    val background = if (selected) {
        Brush.horizontalGradient(
            listOf(VoxelSecondary.copy(alpha = 0.3f), VoxelPrimary.copy(alpha = 0.16f))
        )
    } else {
        Brush.horizontalGradient(listOf(VoxelSurface, VoxelSurface))
    }
    Box(
        modifier = modifier
            .clip(shape)
            .background(background)
            .selectable(selected = selected, onClick = onClick, role = Role.Tab)
            .heightIn(min = 48.dp)
            .padding(horizontal = 8.dp, vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (selected) VoxelTextPrimary else VoxelTextSecondary,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun FollowingUserCard(user: ProfileInfo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, VoxelBorder),
        colors = CardDefaults.cardColors(containerColor = VoxelSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    Modifier.border(
                        2.dp,
                        VoxelPrimary.copy(alpha = 0.45f),
                        CircleShape
                    ).padding(4.dp)
                ) {
                    ProfileImage(profileImage = user.pfp, size = 56)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = user.nick,
                        color = VoxelTextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (user.followers == 1) "1 seguidor" else "${user.followers} seguidores",
                        color = VoxelTextSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            val biography = user.biografia.trim()
            if (biography.isNotEmpty() && biography != "No hay biografia") {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Biografía",
                        color = VoxelTextSecondary,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = biography,
                        color = VoxelTextPrimary,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .background(VoxelPrimary.copy(alpha = 0.10f), RoundedCornerShape(10.dp))
                    .padding(start = 12.dp, end = 6.dp, top = 5.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ver perfil",
                    color = VoxelPrimary,
                    style = MaterialTheme.typography.labelLarge
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = VoxelPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
private fun FollowingContentPreview() {
    FollowingContent(
        state = FollowingState(
            users = listOf(
                ProfileInfo("", "1", "jugadorVoxel", 12, 0f, 0,
                    "Me encantan los videojuegos de aventura y compartir reseñas de mis juegos favoritos. Siempre buscando una nueva historia que descubrir.")
            ),
            isLoading = false
        ),
        onBackClick = {},
        onUserClick = {},
        onRetry = {},
        showFollowers = true
    )
}
