package com.example.voxel_review.ui.screens.Discover

import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.NotificationInfo


data class DiscoverState(
    val genres: List<GenreInfo> = emptyList(),
    val notifications: List<NotificationInfo> = emptyList(),
    val followSuggestions: List<FollowSuggestionInfo> = emptyList(),
    val searchQuery: String = "",
    val  selectedGenre: GenreInfo = LocalGenreProvider.generos.first()

)