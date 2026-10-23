package com.example.voxel_review.ui.utils

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.*

@Composable
fun ContentStatus(message: String, loading: Boolean = false, onRetry: (() -> Unit)? = null) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (loading) CircularProgressIndicator(color = VoxelPrimary, modifier = Modifier.size(28.dp))
        Text(message, color = VoxelTextSecondary, textAlign = TextAlign.Center)
        if (onRetry != null) TextButton(onClick = onRetry) { Text("Reintentar", color = VoxelPrimary) }
    }
}
