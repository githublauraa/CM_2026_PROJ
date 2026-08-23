package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun NotificationsContent(
    onNotificationClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    highlightedNotifications: List<Triple<String, String, Int>>,
    followSuggestions: List<Pair<String, Int>>
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 37.dp,
            end = 37.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            NotificationSectionHeader(
                title = "DESTACADOS",
                showSeeAll = true,
                onSeeAllClick = {
                    // Acción ver todos
                }
            )
        }

        item {
            HighlightedNotificationCard(
                title = "GameFoxy y otros",
                subtitle = "Les gustó tu reseña",
                image = R.drawable.game_profile,
                onClick = {
                    onNotificationClick("game_foxy")
                }
            )
        }

        item {
            HighlightedNotificationCard(
                title = "Nemesis y otros",
                subtitle = "Comentaron tu reseña",
                image = R.drawable.nemesis_profile,
                onClick = {
                    onNotificationClick("nemesis")
                }
            )
        }

        item {
            NotificationSectionHeader(
                title = "Ayer"
            )
        }

        item {
            NewGameNotificationCard(
                gameName = "Resident Evil Requiem",
                onClick = {
                    onNotificationClick("resident_evil_requiem")
                }
            )
        }

        item {
            NotificationSectionHeader(
                title = "Sugerencias de seguimiento",
                showSeeAll = true,
                onSeeAllClick = {
                    // Ver todos los usuarios
                }
            )
        }

        item {
            FollowSuggestionCard(
                username = "Vibeny",
                imageRes = R.drawable.game_profile,
                onClick = {
                    onNotificationClick("vibeny")
                }
            )
        }

        item {
            FollowSuggestionCard(
                username = "Vibepix",
                imageRes = R.drawable.nemesis_profile,
                onClick = {
                    onNotificationClick("vibepix")
                }
            )
        }
    }
}

@Preview
@Composable
fun NotificationsContentPreview() {
    NotificationsContent(
        onNotificationClick = {},
        highlightedNotifications = emptyList(),
        followSuggestions = emptyList()
    )
}