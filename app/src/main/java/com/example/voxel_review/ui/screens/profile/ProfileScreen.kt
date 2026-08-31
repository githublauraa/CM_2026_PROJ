package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.voxel_review.data.profile.LocalProfileProvider
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.utils.DiscoverBottomNavigationBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onClickImage: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by profileViewModel.uiState.collectAsState()

    Box(
        modifier = modifier
        .fillMaxSize()
        .background(VoxelBackground)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 70.dp)
        ) {
            TopBar(
                onBackClick = onBackClick,
                onClickImage = onClickImage
            )
            Pfp(state.profile.pfp)
            UserNick(state.profile.nick)
            Location()
            StatsPanel(state.profile.resenias, state.profile.promedio, state.profile.likes)
            GameCards()
            EditButton()
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ProfileScreen(
        profileViewModel = viewModel(),
        onBackClick = {},
        onClickImage = {}
    )
}
