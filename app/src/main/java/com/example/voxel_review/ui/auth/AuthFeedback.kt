package com.example.voxel_review.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.errorDark

@Composable
fun AuthFieldError(message: String?) {
    if (!message.isNullOrBlank()) Text(
        message, color = errorDark, fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth().semantics { liveRegion = LiveRegionMode.Polite }
    )
}

@Composable
fun AuthLoading() {
    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
}
