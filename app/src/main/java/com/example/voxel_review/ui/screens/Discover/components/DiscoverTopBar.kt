package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun DiscoverTopBar(modifier: Modifier = Modifier, onNotificationClick: () -> Unit = {}, onBackClick: () -> Unit = {}) {
    ScreenHeader("Descubrir", onBackClick, modifier) {
        IconButton(onClick = onNotificationClick) {
            Icon(painterResource(R.drawable.campana_notificaciones), "Notificaciones", tint = White, modifier = Modifier.size(24.dp))
        }
    }
}
