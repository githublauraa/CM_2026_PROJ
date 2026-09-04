package com.example.voxel_review.ui.screens.notifications

import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.NotificationInfo

/**
 * Estado que representa los datos necesarios para renderizar el centro de notificaciones.
 *
 * @property notifications Lista general de notificaciones.
 * @property followSuggestions Lista de sugerencias de cuentas a seguir.
 * @property highlightedNotifications Lista de notificaciones destacadas.
 */
data class NotificationsState(
    val notifications: List<NotificationInfo> = emptyList(),
    val followSuggestions: List<FollowSuggestionInfo> = emptyList(),
    val highlightedNotifications: List<NotificationInfo> = emptyList()
)
