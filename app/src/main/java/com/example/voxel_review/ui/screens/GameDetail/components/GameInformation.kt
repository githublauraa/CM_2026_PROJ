package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelSurface

@Composable
fun GameInformation(
    developer: String,
    releaseDate: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = VoxelSurface),
        border = BorderStroke(1.dp, VoxelBorder)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            GameInfoRow(
                title = "DESARROLLADOR",
                value = developer,
                icon = Icons.Outlined.Business
            )
            HorizontalDivider(color = VoxelBorder)
            GameInfoRow(
                title = "LANZAMIENTO",
                value = releaseDate,
                icon = Icons.Outlined.CalendarMonth
            )
        }
    }
}

@Preview
@Composable
fun GameInformationPreview() {
    GameInformation(developer = "Bethesda Game Studios", releaseDate = "Oct 2023")
}
