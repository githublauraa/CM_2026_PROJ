package com.example.voxel_review.ui.screens.review.components.writeReview

import com.example.voxel_review.ui.screens.review.components.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.*

@Composable
fun GameInfo(
    imagen: Int,
    nombre: String,
    desarrollador: String,
    anio: String,
    modifier: Modifier = Modifier
) {
Row(
        modifier = modifier
            .fillMaxWidth()
            .height(145.dp)
            .background(
                color = VoxelBackground,
                shape = RoundedCornerShape(18.dp)
            )
            .border(
                width = 1.dp,
                color = VoxelSurfaceVariant,
                shape = RoundedCornerShape(18.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        GameImage(
            imagen = imagen,
            descripcion = nombre
        )

        Spacer(
            modifier = Modifier.width(15.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {


                Text(
                    text = nombre,
                    color = VoxelSecondary,
                    fontSize = 15.sp
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = "$desarrollador • $anio",
                    color = White,
                    fontSize = 10.sp
                )
        }
    }
    }
