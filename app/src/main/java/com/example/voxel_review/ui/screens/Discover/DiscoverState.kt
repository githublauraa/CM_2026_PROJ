package com.example.voxel_review.ui.screens.Discover

import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.NotificationInfo

/**
 * Representa el estado de la UI para la pantalla de Discover.
 *
 * @property genres Lista de géneros disponibles.
 * @property notifications Lista de notificaciones recibidas.
 * @property followSuggestions Lista de sugerencias de seguimiento.
 * @property searchQuery Texto actual ingresado en el campo de búsqueda.
 * @property selectedGenre Género seleccionado actualmente para filtrar.
 */

data class DiscoverState(
    val genres: List<GenreInfo> = emptyList(),
    val notifications: List<NotificationInfo> = emptyList(),
    val followSuggestions: List<FollowSuggestionInfo> = emptyList(),
    val trendingSearches: List<TrendingSearchInfo> = emptyList(),
    val searchQuery: String = "",
    val selectedGenre: GenreInfo? = null
)