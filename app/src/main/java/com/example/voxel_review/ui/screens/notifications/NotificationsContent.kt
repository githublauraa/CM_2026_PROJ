package com.example.voxel_review.ui.screens.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotificationContent(
    notificationsViewModel: NotificationsViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NotificationScreen(
        notificationsViewModel = notificationsViewModel,
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        modifier = modifier
    )
}

