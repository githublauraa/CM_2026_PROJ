package com.example.voxel_review.ui.screens.review.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.review.DescriptionSection
import com.example.voxel_review.ui.screens.review.HeroSection
import com.example.voxel_review.ui.screens.review.RatingCard
import com.example.voxel_review.ui.screens.review.UserReviewsSection
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.utils.Boton
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.backgroundDark

@Composable
fun ReviewDetailContent(
    onClickReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val cardBackground =
        colorResource(R.color.voxel_background).copy(alpha = 0.5f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        HeroSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        RatingCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            cardBackgroundColor = cardBackground
        )

        DescriptionSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        UserReviewsSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),

            cardBackgroundColor = cardBackground
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Boton(
            textoBoton = "Hacer reseña",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colorFondo = backgroundDark,
            colorBorde = VoxelPrimary,
            colorLetra = White,
            onClick = onClickReview,
        )

    }
}

@Composable
@Preview
fun ReviewDetailContentPreview(){
    ReviewDetailContent(
        onClickReview = {}
    )
}
