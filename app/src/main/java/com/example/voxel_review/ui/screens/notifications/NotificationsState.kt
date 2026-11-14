package com.example.voxel_review.ui.screens.notifications

import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo

data class NotificationsState(
    val followers: List<NotificationInfo> = emptyList(),
    val isLoading: Boolean = true,
    val hasError: Boolean = false
)
