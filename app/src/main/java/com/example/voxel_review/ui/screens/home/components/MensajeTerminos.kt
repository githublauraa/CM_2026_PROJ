package com.example.voxel_review.ui.screens.home.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

@Composable
fun MensajeTerminos(
    modifier: Modifier = Modifier
){
    Text(
        text = stringResource(R.string.al_continuar_acepta_nuestros_t_rminos_de_servicio),
        color = colorResource(R.color.Voxel_secundario),
        fontSize = 15.sp,
        modifier = modifier
    )
}