package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.ui.theme.VoxelSecondary

/** Shared by the podium and list, including search results. */
@Composable
internal fun RankingUserName(user: ProfileInfo, style: TextStyle) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = user.nick,
            style = style,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f, fill = false)
        )
        if (user.emailVerified) {
            Icon(
                imageVector = Icons.Filled.Verified,
                contentDescription = stringResource(R.string.verified_user),
                tint = VoxelSecondary,
                modifier = Modifier.size(18.dp).testTag("rankingVerifiedBadge_${user.id}")
            )
        }
    }
}
