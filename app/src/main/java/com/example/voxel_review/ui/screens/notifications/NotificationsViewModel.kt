package com.example.voxel_review.ui.screens.notifications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.setValue
import com.example.voxel_review.data.InfoNotifications.FollowSuggestionInfo
import com.example.voxel_review.data.InfoNotifications.LocalDestacadasProvider
import com.example.voxel_review.data.InfoNotifications.LocalSugerenciasProvider
import com.example.voxel_review.data.InfoNotifications.NotificationInfo


class NotificationsViewModel: ViewModel() {

    var highlightedNotifications by mutableStateOf(LocalDestacadasProvider.destacadas)
        private set

    var followSuggestions by mutableStateOf(LocalSugerenciasProvider.sugerenciasSeguimiento)
        private set

}