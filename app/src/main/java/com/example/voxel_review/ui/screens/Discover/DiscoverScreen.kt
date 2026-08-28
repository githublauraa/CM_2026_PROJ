package com.example.voxel_review.ui.screens.Discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.Discover.components.DiscoverTopBar
import com.example.voxel_review.ui.screens.Discover.components.TrendingSearch
import com.example.voxel_review.ui.screens.Discover.components.TrendingSearchesSection
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.screens.Discover.components.GenreFilterSection
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar


@Composable
fun DiscoverScreen(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedGenre by remember {
        mutableStateOf("Todo")
    }

    DiscoverContent(
        searchQuery = searchQuery,
        onSearchQueryChange = {
            searchQuery = it
        },
        selectedGenre = selectedGenre,
        onGenreSelected = {
            selectedGenre = it
        },
        modifier = modifier
    )
}
@Composable
fun DiscoverContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedGenre: String,
    onGenreSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = listOf(
        "Todo",
        "RPG",
        "Acción",
        "Aventura",
        "Estrategia",
        "Indie"
    )

    val trendingSearches = listOf(
        TrendingSearch("Elden Ring", "RPG / Acción"),
        TrendingSearch("Starfield", "Ciencia Ficción"),
        TrendingSearch("Baldur's Gate 3", "RPG de Turnos"),
        TrendingSearch("Borra Gold", "Aventura / Estrategia")
    )
    Box(
        modifier = modifier.fillMaxSize()
    ) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.voxel_background)
            )
            .verticalScroll(
                rememberScrollState()
            )
    ) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            DiscoverTopBar()

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            DiscoverSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            GenreFilterSection(
                genres = genres,
                selectedGenre = selectedGenre,
                onGenreSelected = onGenreSelected
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            TrendingSearchesSection(
                items = trendingSearches,
                onItemClick = {}
            )

            Spacer(
                modifier = Modifier.height(80.dp)
            )
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DiscoverScreenPreview() {
    DiscoverScreen()
}