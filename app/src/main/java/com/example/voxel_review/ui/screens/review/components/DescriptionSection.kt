package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.ui.theme.Voxel_ReviewTheme

@Composable
fun DescriptionSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.obra_maestra),
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = stringResource(R.string.descripcion_juego),
            color = Color.LightGray,
            fontSize = 14.sp,
            lineHeight = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionSectionPreview() {
    Voxel_ReviewTheme {
        DescriptionSection()
    }
}