package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.secondaryDark
import com.example.voxel_review.ui.theme.tertiaryContainerDark

@Composable
fun NotificationSectionHeader(
    title: String,
    showSeeAll: Boolean = false,
    onSeeAllClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            color = tertiaryContainerDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )

        if (showSeeAll) {
            Text(
                text = "Ver todos",
                color = secondaryDark,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(
                    onClick = onSeeAllClick
                )
            )
        }
    }
}

@Preview
@Composable
fun NotificationSectionHeaderPreview() {
    NotificationSectionHeader(
        title = "DESTACADOS",
        showSeeAll = true,
        onSeeAllClick = {}
    )

}
