package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelPrimary
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import com.example.voxel_review.R
import com.example.voxel_review.data.profile.ProfileInfo
import android.net.Uri
import com.example.voxel_review.ui.utils.ProfileImage
@Composable
fun ProfileScreen(
    profileId: String,
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
        onRetry = { profileViewModel.retry() },
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
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {

    val profile = state.profile

    if (profile == null) {
        if (state.isLoading) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(VoxelBackground),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VoxelPrimary)
            }
        }
        return
    }

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

            state.errorMessage?.let { message ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = message,
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(containerColor = VoxelPrimary),
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Text("Reintentar")
                    }
                }
            }

            ProfileImage(
                profileImage = state.profileImageUrl?: "",
                size = 200,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            pickImageButton(onImageSelected = onImageSelected)

            UserNick(profile.nick)

            Location()

            StatsPanel(
                profile.numResenias,
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
        profile = ProfileInfo(
            id = "1",
            pfp = "",
            nick = "juanchoAngara",
            numResenias = 15,
            promedio = 4.5f,
            likes = 120,
            biografia = ""
        ),
        profileImageUrl = null
    )

    ProfileContent(
        state = fakeState,
        onBackClick = {},
        onClickImage = {},
        buttonLogOutPressed = {},
        onImageSelected = {},
        onRetry = {}
    )
}


