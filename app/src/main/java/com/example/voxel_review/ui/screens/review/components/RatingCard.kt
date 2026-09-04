package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        )
    ) {

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CircularRating(
                rating = 4.2f,
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
                    value = 4.5f,
                    maxValue = 5f,
                    color = VoxelPrimary
                )

                RatingBarItem(
                    label = stringResource(R.string.graficos),
                    value = 4.0f,
                    maxValue = 5f,
                    color = VoxelSecondary
                )

                RatingBarItem(
                    label = stringResource(R.string.historia),
                    value = 4.1f,
                    maxValue = 5f,
                    color = VoxelAccentPurple
                )
            }
        }
    }
}