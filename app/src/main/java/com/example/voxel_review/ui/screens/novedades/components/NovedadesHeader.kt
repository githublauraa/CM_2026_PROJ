package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.voxel_review.R
import com.example.voxel_review.ui.utils.HeaderNotificationButton
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun NovedadesHeader(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ScreenHeader(
        title = stringResource(R.string.novedades_y_rese_as),
        onBack = onBackClick,
        modifier = modifier
    ) {
        HeaderNotificationButton(onClick = onNotificationClick)
    }
}
