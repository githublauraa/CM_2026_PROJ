package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Composable
fun GameInfoRow(
    title: String,
    value: String
) {

    Row {

        Text(
            text = "$title: ",
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun GameInfoRowPreview() {

    GameInfoRow(
        title = "DESARROLLADOR",
        value = "Bethesda"
    )
}