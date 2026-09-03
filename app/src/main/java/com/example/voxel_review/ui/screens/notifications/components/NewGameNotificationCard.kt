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
import androidx.compose.material3.Icon
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
import com.example.voxel_review.ui.theme.secondaryDark
import com.example.voxel_review.ui.theme.tertiaryDark


@Composable
fun NewGameNotificationCard(
    gameName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(
                primaryDark
            )
            .border(
                width = 1.dp,
                color = primaryContainerDark,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "¡Nuevo juego añadido!",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = gameName,
                color = tertiaryDark,
                fontSize = 11.sp
            )
        }

        Icon(
            painter = painterResource(R.drawable.northeast_arrow),
            contentDescription = null,
            tint = secondaryDark,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview
@Composable
fun NewGameNotificationCardPreview() {
    NewGameNotificationCard(
        gameName = "Resident Evil Requiem",
        onClick = {}
    )
}