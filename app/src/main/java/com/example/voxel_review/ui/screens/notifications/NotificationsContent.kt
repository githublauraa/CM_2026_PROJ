package com.example.voxel_review.ui.screens.notifications

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo

@Composable
fun NotificationContent(
    notificationsViewModel: NotificationsViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: (NotificationInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    NotificationScreen(
        notificationsViewModel = notificationsViewModel,
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        modifier = modifier
    )
}

