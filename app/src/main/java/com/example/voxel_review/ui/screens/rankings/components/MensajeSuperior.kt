package com.example.voxel_review.ui.screens.rankings.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.theme.White
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
@Composable
fun MensajeSuperior(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Rankings usuarios",
        fontWeight = FontWeight.Bold,
        color = White,
        fontSize = 22.sp,
        modifier = modifier.padding(18.dp)
    )
}


@Preview
@Composable
fun MensajeSuperiorPreview() {
    MensajeSuperior()
}
