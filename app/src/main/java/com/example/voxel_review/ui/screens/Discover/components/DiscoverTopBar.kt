package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.utils.HeaderNotificationButton
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun DiscoverTopBar(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    ScreenHeader(title = "Descubrir", onBack = onBackClick, modifier = modifier) {
        HeaderNotificationButton(onClick = onNotificationClick)
    }
}
