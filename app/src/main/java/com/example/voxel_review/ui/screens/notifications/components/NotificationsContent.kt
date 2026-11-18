package com.example.voxel_review.ui.screens.notifications.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
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
        notificationSection(
            title = R.string.chat_notification_channel,
            keyPrefix = "chat",
            notifications = state.messages,
            error = state.messagesError,
            onNotificationClick = onNotificationClick
        )
        notificationSection(
            title = R.string.new_followers,
            keyPrefix = "follow",
            notifications = state.followers,
            error = state.followersError,
            onNotificationClick = onNotificationClick
        )
        notificationSection(
            title = R.string.review_likes,
            keyPrefix = "like",
            notifications = state.likes,
            error = state.likesError,
            onNotificationClick = onNotificationClick
        )

        val isLoading = state.messagesLoading || state.isLoading || state.likesLoading
        val hasNotifications = state.messages.isNotEmpty() || state.followers.isNotEmpty() || state.likes.isNotEmpty()
        val hasError = state.messagesError != null || state.followersError != null || state.likesError != null
        when {
            isLoading && !hasNotifications -> item { ContentStatus(stringResource(R.string.notices_loading), loading = true) }
            !hasNotifications && !hasError -> item { ContentStatus(stringResource(R.string.no_recent_activity)) }
        }
    }
}

private fun LazyListScope.notificationSection(
    title: Int,
    keyPrefix: String,
    notifications: List<NotificationInfo>,
    error: String?,
    onNotificationClick: (NotificationInfo) -> Unit
) {
    if (notifications.isEmpty() && error == null) return

    item(key = "$keyPrefix-title") { NotificationSectionHeader(title = stringResource(title)) }
    if (notifications.isEmpty() && error != null) {
        item(key = "$keyPrefix-error") { ContentStatus(error) }
    }
    items(notifications, key = { "$keyPrefix-${it.id}" }) { notification ->
        HighlightedNotificationCard(
            notification = notification,
            onClick = { onNotificationClick(notification) }
        )
    }
}

@Preview
@Composable
private fun NotificationsContentPreview() {
    NotificationsContent(
        state = NotificationsState(isLoading = false, likesLoading = false, messagesLoading = false),
        onNotificationClick = {}
    )
}
