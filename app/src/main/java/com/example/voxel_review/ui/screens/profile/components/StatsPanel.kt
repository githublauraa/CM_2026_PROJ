package com.example.voxel_review.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.*

@Composable
fun StatsPanel(
    followers: Int,
    followed: Int,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)
    Row(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(top = 8.dp, bottom = 20.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(
                        VoxelPrimary.copy(alpha = 0.07f),
                        VoxelSurface,
                        VoxelSecondary.copy(alpha = 0.07f)
                    )
                ),
                shape
            )
            .border(1.dp, VoxelBorder.copy(alpha = 0.65f), shape)
            .height(IntrinsicSize.Min)
            .padding(horizontal = 8.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemEstadistica(
            valor = followers,
            etiqueta = "Seguidores",
            icono = Icons.Outlined.Group,
            acento = VoxelPrimary,
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight().padding(vertical = 8.dp),
            color = VoxelBorder
        )
        ItemEstadistica(
            valor = followed,
            etiqueta = "Seguidos",
            icono = Icons.Outlined.PersonAdd,
            acento = VoxelSecondary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ItemEstadistica(
    valor: Int,
    etiqueta: String,
    icono: ImageVector,
    acento: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .semantics(mergeDescendants = true) {}
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = valor.toString(),
            color = White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = acento,
                modifier = Modifier.size(15.dp)
            )
            Text(
                text = etiqueta,
                color = VoxelTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0D1F, widthDp = 360)
@Composable
private fun StatsPanelPreview() {
    StatsPanel(followers = 1280, followed = 256)
}

@Preview(showBackground = true, backgroundColor = 0xFF0F0D1F, widthDp = 320, fontScale = 1.5f)
@Composable
private fun StatsPanelLargeTextPreview() {
    StatsPanel(followers = 0, followed = 0)
}
