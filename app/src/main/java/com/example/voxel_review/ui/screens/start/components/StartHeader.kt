package com.example.voxel_review.ui.screens.start.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.ui.theme.VoxelAccentPurple
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
import com.example.voxel_review.ui.utils.MostrarLogo

@Composable
fun StartHeader(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.fillMaxWidth().height(126.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.matchParentSize()) {
                val logoCenter = Offset(size.width / 2, size.height / 2)
                val glowRadius = 84.dp.toPx()
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(VoxelAccentPurple.copy(alpha = 0.48f), Color.Transparent),
                        center = logoCenter,
                        radius = glowRadius
                    ),
                    radius = glowRadius,
                    center = logoCenter
                )
                drawCircle(
                    color = VoxelPrimary.copy(alpha = 0.20f),
                    radius = 53.dp.toPx(),
                    center = logoCenter,
                    style = Stroke(1.dp.toPx())
                )
                drawVoxel(Offset(size.width * 0.16f, size.height * 0.46f), 19.dp.toPx(), VoxelSecondary)
                drawVoxel(Offset(size.width * 0.29f, size.height * 0.20f), 8.dp.toPx(), VoxelPrimary)
                drawVoxel(Offset(size.width * 0.84f, size.height * 0.38f), 16.dp.toPx(), VoxelPrimary)
                drawVoxel(Offset(size.width * 0.72f, size.height * 0.72f), 9.dp.toPx(), VoxelSecondary)
            }
            MostrarLogo(Modifier.size(126.dp))
        }
        Text(
            text = "Voxel Review",
            style = MaterialTheme.typography.displaySmall.copy(
                brush = Brush.horizontalGradient(
                    listOf(VoxelTextPrimary, Color(0xFFD8C5FF), Color(0xFF9AF7FF))
                ),
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.5).sp
            ),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.explora_rese_a_crea),
            color = onSurfaceVariantDark,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

private fun DrawScope.drawVoxel(center: Offset, radius: Float, color: Color) {
    val halfHeight = radius / 2
    val left = Offset(center.x - radius, center.y - halfHeight)
    val right = Offset(center.x + radius, center.y - halfHeight)
    val bottom = Offset(center.x, center.y + radius)

    val outline = Path().apply {
        moveTo(center.x, center.y - radius)
        lineTo(right.x, right.y)
        lineTo(right.x, right.y + radius)
        lineTo(bottom.x, bottom.y)
        lineTo(left.x, left.y + radius)
        lineTo(left.x, left.y)
        close()
    }
    val strokeWidth = 1.dp.toPx()
    val strokeColor = color.copy(alpha = 0.36f)
    drawPath(outline, color.copy(alpha = 0.07f))
    drawPath(outline, strokeColor, style = Stroke(strokeWidth))
    drawLine(strokeColor, left, center, strokeWidth)
    drawLine(strokeColor, right, center, strokeWidth)
    drawLine(strokeColor, center, bottom, strokeWidth)
}
