package com.example.voxel_review.ui.screens.notifications.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.primaryContainerDark
import com.example.voxel_review.ui.theme.primaryDark
import com.example.voxel_review.ui.theme.tertiaryContainerDark

@Composable
fun HighlightedNotificationCard(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    image: Int
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(61.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                primaryContainerDark
            )
            .border(
                width = 1.dp,
                color = primaryDark,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = subtitle,
                color = tertiaryContainerDark,
                fontSize = 11.sp
            )
        }
    }
}

@Preview
@Composable
fun HighlightedNotificationCardPreview() {
    HighlightedNotificationCard(
        title = "GameFoxy y otros",
        subtitle = "Les gustó tu reseña",
        image = R.drawable.game_profile,
        onClick = {}
    )

}