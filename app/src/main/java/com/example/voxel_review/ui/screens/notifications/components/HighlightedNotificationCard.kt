package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.data.info.InfoNotifications.LocalDestacadasProvider
import com.example.voxel_review.data.info.InfoNotifications.NotificationInfo
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.primaryContainerDark
import com.example.voxel_review.ui.theme.primaryDark
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
fun HighlightedNotificationCard(
    notification: NotificationInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLike = notification.reviewId != null
    val accent = if (isLike) VoxelSecondary else VoxelPrimary
    val action = if (isLike) "Ver reseña" else "Ver perfil"

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(primaryContainerDark)
            .border(
                width = 1.dp,
                color = primaryDark,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClickLabel = action, onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(modifier = Modifier.size(54.dp)) {
            ProfileImage(
                profileImage = notification.imageUrl,
                size = 44
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(24.dp)
                    .background(primaryContainerDark, CircleShape)
                    .border(1.dp, accent, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isLike) Icons.Filled.Favorite else Icons.Outlined.PersonAdd,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = notification.title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = notification.message,
                color = VoxelTextSecondary,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = action,
                color = accent,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
fun HighlightedNotificationCardPreview() {
    HighlightedNotificationCard(
        notification = LocalDestacadasProvider.destacadas.first(),
        onClick = {}
    )
}

@Preview
@Composable
private fun LikeNotificationCardPreview() {
    HighlightedNotificationCard(
        notification = NotificationInfo(
            id = "like",
            title = "PixelRPG",
            message = "Le dio me gusta a tu reseña",
            imageUrl = "",
            reviewId = "review"
        ),
        onClick = {}
    )
}
