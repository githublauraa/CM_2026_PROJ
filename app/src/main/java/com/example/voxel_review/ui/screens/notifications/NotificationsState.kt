package com.example.voxel_review.ui.screens.notifications

import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.NotificationInfo

data class NotificationsState(
    val notifications: List<NotificationInfo> = emptyList(),
    val followSuggestions: List<FollowSuggestionInfo> = emptyList(),
    val highlightedNotifications: List<NotificationInfo> = emptyList()
)
