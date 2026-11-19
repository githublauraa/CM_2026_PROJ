package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.ui.screens.profile.ProfileState
import com.example.voxel_review.ui.screens.profile.calculatePlayerStatistics
import com.example.voxel_review.ui.theme.*
import com.example.voxel_review.ui.utils.ContentStatus
import com.example.voxel_review.ui.utils.libraryStatusLabel
import java.util.Locale

@Composable
fun ProfileStatisticsSection(
    state: ProfileState,
    onRetryReviews: () -> Unit,
    onRetryLibrary: () -> Unit
) {
    if (state.isReviewsLoading || state.isLibraryLoading) {
        ContentStatus(stringResource(R.string.statistics_loading), loading = true)
        return
    }
    if (state.reviewsError != null || state.libraryError != null) {
        ContentStatus(
            state.reviewsError ?: state.libraryError.orEmpty(),
            onRetry = { if (state.reviewsError != null) onRetryReviews(); if (state.libraryError != null) onRetryLibrary() }
        )
        return
    }
    val statistics = remember(state.reviews, state.libraryGames) {
        calculatePlayerStatistics(state.reviews, state.libraryGames)
    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp).testTag("profileStatistics"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(stringResource(R.string.player_statistics), color = White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
        if (state.reviews.isEmpty() && state.libraryGames.isEmpty()) {
            Text(stringResource(R.string.statistics_empty), color = VoxelTextSecondary)
        }
        StatisticsCard(stringResource(R.string.statistics_overview)) {
            SummaryLine(stringResource(R.string.statistics_review_count), statistics.reviewCount.toString())
            SummaryLine(stringResource(R.string.statistics_average), statistics.averageRating?.let {
                String.format(Locale.getDefault(), "%.2f / 5", it)
            } ?: "—")
            SummaryLine(stringResource(R.string.statistics_completed), statistics.libraryCounts[LibraryStatus.COMPLETED].toString())
        }
        StatisticsCard(stringResource(R.string.statistics_collection)) {
            LibraryStatus.entries.forEach { status ->
                StatisticsBar(libraryStatusLabel(status), statistics.libraryCounts.getValue(status), state.libraryGames.size)
            }
        }
        StatisticsCard(stringResource(R.string.statistics_ratings)) {
            Text(stringResource(R.string.statistics_rounding), color = VoxelTextSecondary, fontSize = 12.sp)
            statistics.ratingCounts.forEachIndexed { rating, count ->
                StatisticsBar("$rating ★", count, statistics.ratingCounts.maxOrNull() ?: 0)
            }
        }

    }
}

@Composable
private fun StatisticsCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = VoxelSurface), shape = RoundedCornerShape(18.dp), modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            content()
        }
    }
}

@Composable
private fun SummaryLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(label, color = VoxelTextSecondary, modifier = Modifier.weight(1f))
        Text(value, color = VoxelPrimary, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun StatisticsBar(label: String, count: Int, maximum: Int) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        SummaryLine(label, count.toString())
        Box(Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)).background(VoxelSurfaceVariant)) {
            if (count > 0 && maximum > 0) {
                Box(Modifier.fillMaxWidth((count.toFloat() / maximum).coerceIn(0f, 1f)).height(8.dp)
                    .clip(RoundedCornerShape(4.dp)).background(VoxelPrimary))
            }
        }
    }
}
