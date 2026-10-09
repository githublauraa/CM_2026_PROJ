package com.example.voxel_review.ui.screens.review.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R

/**
 * Barra de navegación superior con opciones de regresar y eliminar el detalle de la reseña.
 *
 * @param modifier Modificador para personalizar la barra superior.
 * @param onBackClick Acción ejecutada al presionar el botón de regreso.
 * @param onDeleteClick Acción ejecutada al presionar el icono de cesta de basura.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,

        title = {
            Text(
                text = stringResource(R.string.resena_completa),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
        },

        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },

        actions = {
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_trash),
                    contentDescription = "Eliminar reseña",
                    tint = Color.White
                )
            }
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}
@Composable
@Preview
fun ReviewDetailTopBarPreview () {
    ReviewDetailTopBar()
}