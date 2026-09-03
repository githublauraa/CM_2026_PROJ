package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameDescription(
    description: String,
    text: String
) {

    Column {

        Text(
            text = "DESCRIPCIÓN",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = description,
            color = Color.White,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
    }
}
@Preview
@Composable
fun GameDescriptionPreview() {

    GameDescription(
        description = "Explora un vasto universo de Bethesda Game Studios. " +
                "Crea tu personaje, navega por mil planetas y descubre misterios " +
                "cósmicos en esta aventura espacial de proporciones épicas.",
        text = "DESCRPCIÓN"
    )
}