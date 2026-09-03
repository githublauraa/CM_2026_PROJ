package com.example.voxel_review.ui.screens.novedades.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.R

@Composable
fun Campana(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(R.drawable.campana_notificaciones),
        contentDescription = stringResource(R.string.notificaciones),
        modifier = modifier.size(27.dp).
        clickable(
            onClick = onClick
        )
    )
}

@Composable
@Preview
fun CampanaPreview(){
    Campana(
        onClick = {}
    )
}