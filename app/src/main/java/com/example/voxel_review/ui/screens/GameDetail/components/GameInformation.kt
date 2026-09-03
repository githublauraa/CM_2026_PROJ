package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.theme.backgroundDark

@Composable
fun GameInformation(
    developer: String,
    releaseDate: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(backgroundDark)
            .border(
                width = 1.dp,
                color = backgroundDark,
                shape = RoundedCornerShape(14.dp)
            )
            .padding(14.dp)
    ) {

        GameInfoRow(
            title = "DESARROLLADOR",
            value = developer
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        GameInfoRow(
            title = "LANZAMIENTO",
            value = releaseDate
        )
    }
}

@Preview
@Composable
fun GameInformationPreview() {

    GameInformation(
        developer = "Bethesda",
        releaseDate = "Oct 2023"
    )
}
