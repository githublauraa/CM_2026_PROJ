package com.example.voxel_review.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.theme.VoxelTextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHeader(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        windowInsets = WindowInsets(0, 0, 0, 0),
        title = { ScreenHeaderTitle(title) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(painterResource(R.drawable.ic_back), "Volver", tint = White, modifier = Modifier.size(24.dp))
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
internal fun ScreenHeaderTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        color = White,
        textAlign = TextAlign.Center
    )
}

@Composable
internal fun HeaderNotificationButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(R.drawable.campana_notificaciones),
            contentDescription = stringResource(R.string.notificaciones),
            tint = VoxelTextPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}
