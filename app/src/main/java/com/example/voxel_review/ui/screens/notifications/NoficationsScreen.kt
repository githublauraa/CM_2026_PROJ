package com.example.voxel_review.ui.screens.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.NotificationInfo
import com.example.voxel_review.ui.screens.notifications.components.NotificationsContent
import com.example.voxel_review.ui.screens.notifications.components.NotificationsTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun NotificationRoute(
    NotificationsViewModel: NotificationsViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {


    //val followSuggestions = remember { LocalNotificationsProvider.sugerenciasSeguimiento }

    NotificationScreen(
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        NotificationsViewModel = NotificationsViewModel,
        modifier = modifier
    )
}

@Composable
fun NotificationScreen(
    NotificationsViewModel: NotificationsViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier,
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
                highlightedNotifications = NotificationsViewModel.highlightedNotifications,
                followSuggestions = NotificationsViewModel.followSuggestions,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

/*@Preview
@Composable
fun NotificationSreenPreview() {
    NotificationScreen(
        onBackClick = {},
        onNotificationClick = {},
        NotificationsViewModel = ViewModel(),

    )
}*/