package com.example.voxel_review.ui.screens.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ChatHistoryButton(
    label: String,
    isLoading: Boolean,
    error: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
        TextButton(onClick = onClick, enabled = !isLoading) {
            Text(if (isLoading) "Cargando…" else label)
        }
    }
}
