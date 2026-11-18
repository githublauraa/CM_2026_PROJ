package com.example.voxel_review.ui.screens.chat.conversation.components

import com.example.voxel_review.R
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelSurfaceVariant
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ChatDayDivider(day: LocalDate, modifier: Modifier = Modifier) {
    val locale = LocalConfiguration.current.locales[0]
    val formatter = remember(locale) { DateTimeFormatter.ofPattern("d MMM yyyy", locale) }
    val today = LocalDate.now()
    val label = when (day) {
        today -> stringResource(R.string.chat_today)
        today.minusDays(1) -> stringResource(R.string.chat_yesterday)
        else -> day.format(formatter)
    }

    Box(
        modifier = modifier.fillMaxWidth().padding(top = 10.dp, bottom = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            modifier = Modifier.background(VoxelSurfaceVariant, RoundedCornerShape(50))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            color = Color(0xFFE0DCF0),
            fontFamily = FontFamily.SansSerif,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
