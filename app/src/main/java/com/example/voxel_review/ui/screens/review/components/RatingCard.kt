package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelBorder
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelAccentPurple
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary

/**
 * Tarjeta contenedora que muestra el desglose del puntaje general e indicadores específicos del juego.
 *
 * @param modifier Modificador para personalizar la tarjeta.
 * @param cardBackgroundColor Color de fondo asignado a la tarjeta.
 */
@Composable
fun RatingCard(
    ratingGeneral: Float,
    ratingJugabilidad: Float,
    ratingGraficos: Float,
    ratingHistoria: Float,
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, VoxelBorder),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        )
    ) {

        Text(
                fontFamily = FontFamily.SansSerif,
            text = "Calificaciones",
            color = VoxelTextPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 18.dp, top = 18.dp)
        )
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CircularRating(
                rating = ratingGeneral,
                maxRating = 5,
                color = VoxelPrimary
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                RatingBarItem(
                    label = stringResource(R.string.jugabilidad),
                    value = ratingJugabilidad,
                    maxValue = 5f,
                    color = VoxelPrimary
                )

                RatingBarItem(
                    label = stringResource(R.string.graficos),
                    value = ratingGraficos,
                    maxValue = 5f,
                    color = VoxelSecondary
                )

                RatingBarItem(
                    label = stringResource(R.string.historia),
                    value = ratingHistoria,
                    maxValue = 5f,
                    color = VoxelAccentPurple
                )
            }
        }
    }
}