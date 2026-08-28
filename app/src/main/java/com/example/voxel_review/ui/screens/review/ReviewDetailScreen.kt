package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.R
import com.example.voxel_review.data.infoJuegosNovedades.JuegoInfo
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailScreen(
    onClickReview: () -> Unit,
    juego: JuegoInfo,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize(),
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.TopCenter),
        ) {
            ReviewDetailTopBar(
                onBackClick = onBackClick
            )
            ReviewDetailContent(
                onClickReview = onClickReview
            )
        }
    }
}

@Composable
@Preview
fun ReviewDetailScreenPreview() {
    ReviewDetailScreen(
        onClickReview = {},
        juego = JuegoInfo (
            imagen = R.drawable.logo_fortnite,
            etiqueta = 1,
            nombre = 1,
            descripcion = 1,
            autor = 1,
            calificacion = 1
        ),
        onBackClick = {}
    )

}
