package com.example.voxel_review.ui.screens.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.chat.ChatContact
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
fun ChatAvatar(contact: ChatContact, size: Int, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(size.dp).background(VoxelSurfaceVariant, CircleShape)
            .border(1.dp, VoxelBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (contact.photoUrl.isBlank()) {
            Text(
                text = contact.name.trim().take(1).uppercase().ifBlank { "?" },
                color = VoxelPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
        } else {
            ProfileImage(profileImage = contact.photoUrl, size = size - 4)
        }
    }
}
