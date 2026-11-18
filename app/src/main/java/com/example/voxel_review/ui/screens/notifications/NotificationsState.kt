package com.example.voxel_review.ui.screens.notifications

import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo

data class NotificationsState(
    val followers: List<NotificationInfo> = emptyList(),
    val isLoading: Boolean = true,
    val followersError: String? = null,
    val likes: List<NotificationInfo> = emptyList(),
    val likesLoading: Boolean = true,
    val likesError: String? = null,
    val messages: List<NotificationInfo> = emptyList(),
    val messagesLoading: Boolean = true,
    val messagesError: String? = null
)
