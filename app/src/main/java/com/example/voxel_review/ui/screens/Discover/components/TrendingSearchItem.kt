package com.example.voxel_review.ui.screens.Discover.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.onSurfaceVariantDark

@Composable
fun TrendingSearchesSection(
    items: List<TrendingSearch>,
    onItemClick: (TrendingSearch) -> Unit,
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
                TrendingSearchItem(item = item, onClick = { onItemClick(item) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TrendingSearchesSectionPreview() {
    TrendingSearchesSection(
        items = listOf(
            TrendingSearch("Elden Ring", "RPG / Acción"),
            TrendingSearch("Starfield", "Ciencia Ficción"),
            TrendingSearch("Baldur's Gate 3", "RPG de Turnos"),
            TrendingSearch("Borra Gold", "Aventura / Estrategia")
        ),
        onItemClick = {}
    )
}
