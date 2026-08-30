package com.example.voxel_review.ui.screens.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.LocalNotificationsProvider
import com.example.voxel_review.data.InfoNotifications.NotificationInfo
import com.example.voxel_review.ui.screens.notifications.components.NotificationsContent
import com.example.voxel_review.ui.screens.notifications.components.NotificationsTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun NotificationRoute(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val highlightedNotifications = remember { LocalNotificationsProvider.destacadas }
    val followSuggestions = remember { LocalNotificationsProvider.sugerenciasSeguimiento }

    NotificationScreen(
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        highlightedNotifications = highlightedNotifications,
        followSuggestions = followSuggestions,
        modifier = modifier
    )
}

@Composable
fun NotificationScreen(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
    highlightedNotifications: List<NotificationInfo> = LocalNotificationsProvider.destacadas,
    followSuggestions: List<FollowSuggestionInfo> = LocalNotificationsProvider.sugerenciasSeguimiento,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            NotificationsTopBar(
                onBackClick = onBackClick
            )

            NotificationsContent(
                highlightedNotifications = highlightedNotifications,
                followSuggestions = followSuggestions,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

@Preview
@Composable
fun NotificationSreenPreview() {
    NotificationScreen(
        onBackClick = {},
        onNotificationClick = {}
    )
}