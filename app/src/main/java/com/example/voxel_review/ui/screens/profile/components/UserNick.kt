package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelSecondary

@Composable
fun UserNick(
    nick: String,
    isVerified: Boolean = false,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = nick,
                modifier = Modifier.weight(1f, fill = false),
                style = MaterialTheme.typography.headlineMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
            if (isVerified) {
                Icon(
                    imageVector = Icons.Filled.Verified,
                    contentDescription = "Usuario verificado",
                    tint = VoxelSecondary,
                    modifier = Modifier
                        .size(22.dp)
                        .testTag("verifiedBadgeIcon")
                )
            }
        }
        if (isVerified) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Usuario verificado",
                color = VoxelSecondary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .testTag("verifiedBadgeLabel")
            )
        }
    }
}
