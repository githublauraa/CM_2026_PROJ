package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.LocalTrendingSearchProvider
import com.example.voxel_review.data.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun TrendingSearchesSection(
    items: List<TrendingSearchInfo>,
    onItemClick: (TrendingSearchInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "TENDENCIAS DE BÚSQUEDA",
            color = onSurfaceVariantDark,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )

        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEach { item ->
                TrendingSearchItem(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Preview()
@Composable
private fun TrendingSearchesSectionPreview() {
    TrendingSearchesSection(
        items = LocalTrendingSearchProvider.tendencias,
        onItemClick = { /*pantalla detalle juego*/
        }
    )
}

