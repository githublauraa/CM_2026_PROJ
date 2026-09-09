package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.rememberScrollState
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
@Composable
fun ProfileScreen(
    profileId: Int,
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onClickImage: () -> Unit,
    buttonLogOutPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by profileViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.getProfileById(profileId)
    }

    val profile = state.profile ?: return
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoxelBackground)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 70.dp).
                verticalScroll(rememberScrollState())
        ) {
            TopBar(
                onBackClick = onBackClick,
                onClickImage = onClickImage
            )
            Pfp(profile.pfp)
            UserNick(profile.nick)
            Location()
            StatsPanel(profile.resenias, profile.promedio, profile.likes)
            GameCards()
            EditButton()
            Spacer(modifier = Modifier.height(5.dp))
            ButtonLogOut(
                buttonLogOutPressed = {
                    profileViewModel.logOut()
                    buttonLogOutPressed()
                }
            )
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ProfileScreen(
        profileId = 1,
        profileViewModel = viewModel(),
        onBackClick = {},
        onClickImage = {},
        buttonLogOutPressed = {}
    )
}
