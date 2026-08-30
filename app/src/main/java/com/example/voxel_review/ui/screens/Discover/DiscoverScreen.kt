package com.example.voxel_review.ui.screens.Discover

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.ui.screens.Discover.components.DiscoverTopBar
import com.example.voxel_review.ui.screens.Discover.components.TrendingSearchesSection
import com.example.voxel_review.R
import com.example.voxel_review.data.InfoDiscover.GenreInfo
import com.example.voxel_review.data.InfoDiscover.LocalTrendingSearchProvider
import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider
import com.example.voxel_review.data.InfoDiscover.TrendingSearchInfo
import com.example.voxel_review.ui.screens.Discover.components.DiscoverSearchBar
import com.example.voxel_review.ui.screens.Discover.components.GenreFilterSection


@Composable
fun DiscoverRoute(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    selectedGenre: GenreInfo,
    tendencias: List<TrendingSearchInfo>,
    generos: List<GenreInfo>,

    modifier: Modifier = Modifier
) {

    var searchQuery by remember { mutableStateOf("") }
    var selectedGenre by remember { mutableStateOf(LocalGenreProvider.generos.first()) }

    DiscoverScreen(
        onBackClick = onBackClick,
        onNotificationClick = onNotificationClick,
        searchQuery = searchQuery,
        selectedGenre = selectedGenre,
        onSearchQueryChange = { searchQuery = it },
        onGenreSelected = { selectedGenre = it },
        modifier = modifier
    )
}

@Composable
fun DiscoverScreen(
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onGenreSelected: (GenreInfo) -> Unit,
    modifier: Modifier = Modifier,
    selectedGenre: GenreInfo = LocalGenreProvider.generos.first(),
    tendencias: List<TrendingSearchInfo> = LocalTrendingSearchProvider.tendencias,
    generos: List<GenreInfo> = LocalGenreProvider.generos,

    ) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.voxel_background))
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            DiscoverTopBar(
                onBackClick = onBackClick,
                onNotificationClick = onNotificationClick
            )

            Spacer(modifier = Modifier.height(12.dp))

            DiscoverSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,

            )

            Spacer(modifier = Modifier.height(20.dp))

            GenreFilterSection(
                genres = generos,
                selectedGenre = selectedGenre,
                onGenreSelected = onGenreSelected
            )

            Spacer(modifier = Modifier.height(20.dp))

            TrendingSearchesSection(
                items = tendencias,
                onItemClick = {

                }
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}
@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DiscoverScreenPreview() {
    DiscoverScreen(
        onBackClick = {},
        onNotificationClick = {},
        searchQuery = "",
        selectedGenre = LocalGenreProvider.generos.first(),
        onSearchQueryChange = {},
        onGenreSelected = {}
    )
}
