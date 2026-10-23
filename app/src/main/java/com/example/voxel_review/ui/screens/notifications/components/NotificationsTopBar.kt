package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun NotificationsTopBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    ScreenHeader("Notificaciones", onBackClick, modifier)
}
