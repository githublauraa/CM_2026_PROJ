package com.example.voxel_review.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.White

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
        title = { Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = White) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(painterResource(R.drawable.ic_back), "Volver", tint = White, modifier = Modifier.size(24.dp))
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}
