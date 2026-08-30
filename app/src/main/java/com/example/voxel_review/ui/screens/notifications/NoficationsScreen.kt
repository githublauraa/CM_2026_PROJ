package com.example.voxel_review.ui.screens.notifications

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.notifications.components.NotificationsContent
import com.example.voxel_review.ui.screens.notifications.components.NotificationsTopBar
import com.example.voxel_review.ui.utils.FondoPantalla
@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onNotificationClick: (String) -> Unit = {}
) {
    //cambiar a data class Notification(val title: String, val message: String, val imageResId: Int)
    val highlightedNotifications = listOf(
        Triple(
            "GameFoxy y otros",
            "Les gustó tu reseña",
            R.drawable.game_profile
        ),
        Triple(
            "Nemesis y otros",
            "Comentaron tu reseña",
            R.drawable.nemesis_profile
        )
    )

    val followSuggestions = listOf(
        Pair(
            "Vibeny",
            R.drawable.game_profile
        ),
        Pair(
            "Vibepix",
            R.drawable.nemesis_profile
        )
    )

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
                onNotificationClick = onNotificationClick
            )
        }
    }
}

@Preview
@Composable
fun NotificationSreenPreview() {
    NotificationScreen(
        onBackClick = {}
    )
}