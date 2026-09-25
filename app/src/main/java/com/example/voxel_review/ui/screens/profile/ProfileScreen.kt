package com.example.voxel_review.ui.screens.profile

import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material3.Text
import androidx.compose.foundation.rememberScrollState
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import com.example.voxel_review.R
import com.example.voxel_review.data.profile.Profile
import androidx.compose.material3.Button
import android.util.Log
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
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

    LaunchedEffect(profileId) {
        profileViewModel.getProfileById(profileId)
    }

    ProfileContent(
        state = state,
        onImageSelected = { profileViewModel.uploadImageFireBase(it) },
        onBackClick = onBackClick,
        onClickImage = onClickImage,
        buttonLogOutPressed = {
            profileViewModel.logOut()
            buttonLogOutPressed()
        },
        modifier = modifier
    )
}

@Composable
fun ProfileContent(
    state: ProfileState,
    onBackClick: () -> Unit,
    onClickImage: () -> Unit,
    buttonLogOutPressed: () -> Unit,
    onImageSelected: (Uri) -> Unit,
    modifier: Modifier = Modifier
) {

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
                .padding(bottom = 70.dp)
                .verticalScroll(rememberScrollState())
        ) {

            TopBar(
                onBackClick = onBackClick,
                onClickImage = onClickImage
            )

            Pfp(state.profileImageUrl)

            pickImageButton(onImageSelected = onImageSelected)

            UserNick(profile.nick)

            Location()

            StatsPanel(
                profile.resenias,
                profile.promedio,
                profile.likes
            )

            GameCards()

            EditButton()

            Spacer(modifier = Modifier.height(5.dp))

            ButtonLogOut(
                buttonLogOutPressed = buttonLogOutPressed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {

    val fakeState = ProfileState(
        profile = Profile(
            id = 1,
            pfp = R.drawable.profile_picture,
            nick = R.string.nick,
            resenias = 15,
            promedio = 4.5f,
            likes = 120
        ),
        profileImageUrl = null
    )

    ProfileContent(
        state = fakeState,
        onBackClick = {},
        onClickImage = {},
        buttonLogOutPressed = {},
        onImageSelected = {}
    )
}


