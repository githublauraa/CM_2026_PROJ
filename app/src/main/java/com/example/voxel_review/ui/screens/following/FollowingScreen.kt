package com.example.voxel_review.ui.screens.following

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.theme.VoxelBackground
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
    LaunchedEffect(userId, showFollowers) {
        followingViewModel.loadUsers(userId, showFollowers)
    }
    FollowingContent(
        state = state,
        showFollowers = showFollowers,
        onBackClick = onBackClick,
        onUserClick = onUserClick,
        onRetry = { followingViewModel.loadUsers(userId, showFollowers) },
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
    showFollowers: Boolean = false
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = VoxelBackground,
        topBar = { ScreenHeader(if (showFollowers) "Seguidores" else "Seguidos", onBackClick) }
    ) { padding ->
        when {
            state.isLoading -> Column(Modifier.padding(padding)) {
                ContentStatus(
                    if (showFollowers) "Cargando seguidores…" else "Cargando personas seguidas…",
                    loading = true
                )
            }
            state.error != null -> Column(Modifier.padding(padding)) {
                ContentStatus(state.error, onRetry = onRetry)
            }
            state.users.isEmpty() -> Column(Modifier.padding(padding)) {
                ContentStatus(
                    if (showFollowers) "Este usuario todavía no tiene seguidores"
                    else "Este usuario todavía no sigue a nadie"
                )
            }
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.users, key = { it.id }) { user ->
                    FollowingUserCard(user = user, onClick = { onUserClick(user.id) })
                }
            }
        }
    }
}

@Composable
private fun FollowingUserCard(user: ProfileInfo, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VoxelSurface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileImage(profileImage = user.pfp, size = 56)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = user.nick,
                    color = VoxelTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.biografia,
                    color = VoxelTextSecondary,
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FollowingContentPreview() {
    FollowingContent(
        state = FollowingState(
            users = listOf(
                ProfileInfo("", "1", "jugadorVoxel", 0, 0f, 0,
                    "Me encantan los videojuegos de aventura y compartir reseñas de mis juegos favoritos. Siempre buscando una nueva historia que descubrir.")
            ),
            isLoading = false
        ),
        onBackClick = {},
        onUserClick = {},
        onRetry = {}
    )
}
