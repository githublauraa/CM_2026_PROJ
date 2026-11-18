package com.example.voxel_review.ui.screens.Discover.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
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
    ScreenHeader(title = stringResource(R.string.nav_discover), onBack = onBackClick, modifier = modifier) {
        HeaderNotificationButton(onClick = onNotificationClick)
    }
}
