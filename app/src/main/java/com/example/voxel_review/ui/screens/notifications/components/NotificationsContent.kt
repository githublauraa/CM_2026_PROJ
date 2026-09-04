package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.NotificationInfo
import kotlin.collections.emptyList

/**
 * Contenido principal y estructurado para la pantalla de notificaciones.
 * Organiza las notificaciones destacadas, avisos de nuevos juegos y sugerencias mediante una lista.
 *
 * @param highlightedNotifications Lista de notificaciones destacadas a mostrar.
 * @param followSuggestions Lista de sugerencias de usuarios a seguir.
 * @param onNotificationClick Callback ejecutado al presionar cualquier elemento de notificación.
 * @param modifier Modificador para el contenedor de la lista.
 */
@Composable
fun NotificationsContent(
    highlightedNotifications: List<NotificationInfo>,
    followSuggestions: List<FollowSuggestionInfo>,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 37.dp,
            end = 37.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            NotificationSectionHeader(
                title = "DESTACADOS",
                showSeeAll = true,
                onSeeAllClick = {}
            )
        }

        items(highlightedNotifications) { item ->
            HighlightedNotificationCard(
                notification = item,
                onClick = { onNotificationClick() }
            )
        }

        item {
            NotificationSectionHeader(
                title = "Ayer"
            )
        }

        item {
            NewGameNotificationCard(
                gameName = "Resident Evil Requiem",
                onClick = { onNotificationClick() }
            )
        }

        item {
            NotificationSectionHeader(
                title = "Sugerencias de seguimiento",
                showSeeAll = true,
                onSeeAllClick = { }
            )
        }

        items(followSuggestions) { item ->
            FollowSuggestionCard(
                suggestion = item,
                onClick = { onNotificationClick() }
            )
        }
    }
}

@Preview
@Composable
fun NotificationsContentPreview() {
    NotificationsContent(
        onNotificationClick = {},
        highlightedNotifications = emptyList(),
        followSuggestions = emptyList()
    )
}