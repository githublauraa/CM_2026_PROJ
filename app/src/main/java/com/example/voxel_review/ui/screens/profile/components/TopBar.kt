package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.ScreenHeader

@Composable
fun TopBar(onBackClick: () -> Unit, onClickImage: () -> Unit, modifier: Modifier = Modifier) {
    ScreenHeader("Mi Perfil", onBackClick, modifier) {
        IconButton(onClick = onClickImage) {
            Icon(painterResource(R.drawable.settings), "Configuración", tint = White, modifier = Modifier.size(24.dp))
        }
    }
}
