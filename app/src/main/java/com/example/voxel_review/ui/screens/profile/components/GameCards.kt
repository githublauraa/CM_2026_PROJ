package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
fun GameCards(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        Text(
            text = "Mis Juegos",
            color = White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            GameCard(
                imagen = ""
            )

            GameCard(
                imagen = ""
            )

            GameCard(
                imagen = ""
            )
        }
    }
}

@Composable
fun GameCard(
    imagen: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(120.dp)
            .height(170.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors()
    ) {

        ProfileImage(
            profileImage = imagen,
            size = 120,
            modifier = Modifier
                .width(120.dp)
                .height(170.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Preview
@Composable
fun GameCardsPreview() {
    GameCards()
}

@Preview
@Composable
fun GameCardPreview() {
    GameCard(
        imagen = ""
    )
}