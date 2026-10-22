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
import androidx.compose.foundation.rememberScrollState
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import com.google.firebase.auth.FirebaseAuth
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import android.net.Uri
import android.util.Log
import com.example.voxel_review.ui.utils.ProfileImage
@Composable
fun ProfileScreen(
    profileId: String,
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onClickImage: () -> Unit,
    buttonLogOutPressed: () -> Unit,
    onReviewClick: (ReviewInfo) -> Unit,
    onEditProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by profileViewModel.uiState.collectAsState()

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val isOwnProfile = currentUserId != null && profileId == currentUserId

    LaunchedEffect(profileId) {
        profileViewModel.getProfileById(profileId)
        profileViewModel.getUserReviews(profileId)
    }

    ProfileContent(
        state = state,
        onImageSelected = { profileViewModel.uploadImageFireBase(it) },
        onBackClick = onBackClick,
        onClickImage = onClickImage,
        onReviewClick = onReviewClick,
        buttonLogOutPressed = {
            profileViewModel.logOut()
            buttonLogOutPressed()
        },
        isOwnProfile = isOwnProfile,
        onEditProfileClick = onEditProfileClick,
        buttonFollowPressed = {
            profileViewModel.followOrUnfollow(profileId)
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
    onReviewClick: (ReviewInfo) -> Unit,
    isOwnProfile: Boolean = false,
    onEditProfileClick: () -> Unit,
    buttonFollowPressed: ()-> Unit,
    modifier: Modifier = Modifier
) {

    val profile = state.profile ?: return
    val reviews = state.reviews ?: return

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

            ProfileImage(
                profileImage = state.profileImageUrl?: "",
                size = 200,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (isOwnProfile) {
                pickImageButton(onImageSelected = onImageSelected)
            }

            UserNick(profile.nick)

            Biography(
                biografia = state.profile?.biografia
            )

            if (!isOwnProfile){
                ButtonFollow(
                    buttonFollowPressed = {
                        buttonFollowPressed()
                        Log.d("seguirUsuario", "El usuario ${state.currentUserId} esta tratando de seguir a ${state.profile.id}")
                    }
                )
            }

            Spacer(modifier = Modifier.height(9.dp))


            StatsPanel(
                profile.followers,
                profile.following,
                profile.likes
            )

            GameCards(
                reviews = reviews,
                onReviewClick = onReviewClick
            )

            if (isOwnProfile) {
                EditButton(
                    onEditProfileClick = onEditProfileClick
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            if (isOwnProfile) {
                ButtonLogOut(buttonLogOutPressed = buttonLogOutPressed)
            }
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
        onReviewClick = {},
        onEditProfileClick = {} ,
        buttonFollowPressed = {}
    )
}


