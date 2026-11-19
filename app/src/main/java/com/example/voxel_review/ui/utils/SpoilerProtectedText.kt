package com.example.voxel_review.ui.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary

/** Hidden text is not composed, so it cannot leak through accessibility or selection. */
@Composable
fun SpoilerProtectedText(
    reviewId: String,
    text: String,
    containsSpoilers: Boolean,
    modifier: Modifier = Modifier,
    color: Color = VoxelTextSecondary,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    maxLines: Int = Int.MAX_VALUE
) {
    var revealed by remember(reviewId, text, containsSpoilers) { mutableStateOf(false) }
    Column(modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        if (containsSpoilers) {
            Text(stringResource(R.string.review_spoiler_warning), color = VoxelTextSecondary, style = MaterialTheme.typography.labelMedium)
        }
        if (!containsSpoilers || revealed) {
            Text(text, color = color, style = style, maxLines = maxLines, overflow = TextOverflow.Ellipsis)
        }
        if (containsSpoilers) {
            TextButton(
                onClick = { revealed = !revealed },
                modifier = Modifier.testTag("toggleSpoiler_$reviewId")
            ) {
                Text(stringResource(if (revealed) R.string.review_hide_spoilers else R.string.review_show_spoilers), color = VoxelPrimary)
            }
        }
    }
}
