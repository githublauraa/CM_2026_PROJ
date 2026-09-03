package com.example.voxel_review.ui.screens.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.LocalDestacadasProvider
import com.example.voxel_review.data.InfoNotifications.LocalSugerenciasProvider
import com.example.voxel_review.data.InfoNotifications.NotificationInfo
import com.example.voxel_review.ui.screens.notifications.components.NotificationsContent
import com.example.voxel_review.ui.screens.notifications.components.NotificationsTopBar
import com.example.voxel_review.ui.utils.FondoPantalla


@Composable
fun NotificationRoute(
    notificationsViewModel: NotificationsViewModel,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by notificationsViewModel.uiState.collectAsState()

    NotificationScreen(
        highlightedNotifications = state.highlightedNotifications,
        followSuggestions = state.followSuggestions,
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        modifier = modifier
    )
}

@Composable
fun NotificationScreen(
    highlightedNotifications: List<NotificationInfo>,
    followSuggestions: List<FollowSuggestionInfo>,
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
                highlightedNotifications = highlightedNotifications,
                followSuggestions = followSuggestions,
                onNotificationClick = onNotificationClick,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable fun NotificationScreenPreview() {
    NotificationScreen( highlightedNotifications = LocalDestacadasProvider.destacadas,
        followSuggestions = LocalSugerenciasProvider.sugerenciasSeguimiento,
        onBackClick = {},
        onNotificationClick = {}
    )
}