package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.notifications.NotificationsState
import com.example.voxel_review.ui.utils.ContentStatus

@Composable
fun NotificationsContent(
    state: NotificationsState,
    onNotificationClick: (NotificationInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            NotificationSectionHeader(title = "Nuevos seguidores")
        }

        when {
            state.isLoading -> item { ContentStatus("Cargando notificaciones…") }
            state.hasError -> item { ContentStatus("No se pudieron cargar las notificaciones.") }
            state.followers.isEmpty() -> item { ContentStatus("Todavía no tienes avisos de seguimiento.") }
            else -> items(state.followers, key = { it.id }) { follower ->
                HighlightedNotificationCard(
                    notification = follower,
                    onClick = { onNotificationClick(follower) }
                )
            }
        }

        item {
            NotificationSectionHeader(title = "Me gusta en tus reseñas")
        }

        when {
            state.likesLoading -> item { ContentStatus("Cargando me gusta…") }
            state.likesError -> item { ContentStatus("No se pudieron cargar los me gusta.") }
            state.likes.isEmpty() -> item { ContentStatus("Tus reseñas aún no tienen me gusta de otros usuarios.") }
            else -> items(state.likes, key = { it.id }) { like ->
                HighlightedNotificationCard(
                    notification = like,
                    onClick = { onNotificationClick(like) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun NotificationsContentPreview() {
    NotificationsContent(
        state = NotificationsState(isLoading = false),
        onNotificationClick = {}
    )
}
