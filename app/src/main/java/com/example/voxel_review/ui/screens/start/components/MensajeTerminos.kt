package com.example.voxel_review.ui.screens.start.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun MensajeTerminos(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(
            R.string.al_continuar_acepta_nuestros_t_rminos_de_servicio
        ),
        color = onSurfaceVariantDark,
        fontSize = 10.sp,
        modifier = modifier
    )
}

@Composable
@Preview
fun MensajeTerminosPreview() {
    MensajeTerminos()
}