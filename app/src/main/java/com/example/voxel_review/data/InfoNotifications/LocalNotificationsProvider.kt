package com.example.voxel_review.data.InfoNotifications

import com.example.voxel_review.R

object LocalNotificationsProvider {
    val destacadas = listOf(
        NotificationInfo(
            id = "game_foxy",
            title = "GameFoxy y otros",
            message = "Les gustó tu reseña",
            imageResId = R.drawable.game_profile
        ),
        NotificationInfo(
            id = "nemesis",
            title = "Nemesis y otros",
            message = "Comentaron tu reseña",
            imageResId = R.drawable.nemesis_profile
        )
    )

    val sugerenciasSeguimiento = listOf(
        FollowSuggestionInfo(
            id = "vibeny",
            username = "Vibeny",
            imageResId = R.drawable.game_profile
        ),
        FollowSuggestionInfo(
            id = "vibepix",
            username = "Vibepix",
            imageResId = R.drawable.nemesis_profile
        )
    )
}