package com.example.voxel_review.ui.screens.GameDetail.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.theme.tertiaryDark

@Composable
fun WriteReviewButton(
    onClick: () -> Unit,
    modifier: Modifier
) {

    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = tertiaryDark
    ) {

        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Escribir reseña",
            tint = Color.White
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFF0F0D1F
)
@Composable
fun WriteReviewButtonPreview() {
    WriteReviewButton(
        onClick = {},
        modifier = Modifier
    )
}