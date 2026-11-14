package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun Biography(biografia: String, modifier: Modifier = Modifier) {
    val bio = biografia.trim()
    Column(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = stringResource(R.string.register_bio),
            style = MaterialTheme.typography.titleSmall,
            color = VoxelTextPrimary
        )
        Text(
            text = if (bio.isEmpty() || bio == "No hay biografia") {
                stringResource(R.string.profile_empty_biography)
            } else {
                bio
            },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = onSurfaceVariantDark
        )
    }
}
