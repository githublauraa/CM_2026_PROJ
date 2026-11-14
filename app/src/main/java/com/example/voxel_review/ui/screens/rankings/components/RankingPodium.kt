package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
internal fun RankingPodium(
    leaders: List<IndexedValue<ProfileInfo>>,
    onUserClick: (ProfileInfo) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (index in listOf(1, 0, 2)) {
            val leader = leaders[index]
            val position = leader.index + 1
            val accent = medalColor(position)
            Surface(
                onClick = { onUserClick(leader.value) },
                modifier = Modifier.weight(1f).testTag("profile"),
                shape = RoundedCornerShape(16.dp),
                color = VoxelSurface,
                contentColor = VoxelTextPrimary,
                border = BorderStroke(1.dp, accent.copy(alpha = if (index == 0) 0.55f else 0.24f))
            ) {
                Column(
                    modifier = Modifier
                        .background(Brush.verticalGradient(listOf(accent.copy(alpha = 0.13f), VoxelSurface)))
                        .padding(horizontal = 7.dp)
                        .padding(top = if (index == 0) 14.dp else 10.dp, bottom = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Text(
                        text = "#$position",
                        color = accent,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                    ProfileImage(profileImage = leader.value.pfp, size = if (index == 0) 54 else 40)
                    Text(
                        text = leader.value.nick,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (leader.value.numResenias == 1) "1 reseña" else "${leader.value.numResenias} reseñas",
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        color = accent
                    )
                }
            }
        }
    }
}
