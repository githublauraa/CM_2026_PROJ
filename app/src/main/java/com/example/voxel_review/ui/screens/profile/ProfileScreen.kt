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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSecondary
import com.example.voxel_review.ui.theme.VoxelSurface
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
    onFollowingClick: () -> Unit = {},
    onFollowersClick: () -> Unit = {},
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
        onRetry = { profileViewModel.getProfileById(profileId) },
        onRetryReviews = { profileViewModel.getUserReviews(profileId) },
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
        onFollowingClick = onFollowingClick,
        onFollowersClick = onFollowersClick,
        buttonFollowPressed = {
            profileViewModel.followOrUnfollow(profileId)
        },
        onVerifyEmailClick = {
            profileViewModel.verifyOrSendEmailVerification()
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
    onFollowingClick: () -> Unit = {},
    onFollowersClick: () -> Unit = {},
    buttonFollowPressed: ()-> Unit,
    onVerifyEmailClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    onRetryReviews: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val profile = state.profile
    if (profile == null) {
        Column(modifier = modifier.fillMaxSize().background(VoxelBackground)) {
            TopBar(onBackClick = onBackClick, onClickImage = onClickImage)
            com.example.voxel_review.ui.utils.ContentStatus(
                message = state.errorMessage ?: "Cargando perfil…",
                loading = state.errorMessage == null,
                onRetry = if (state.errorMessage != null) onRetry else null
            )
        }
        return
    }
    val reviews = state.reviews

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoxelBackground)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {

            TopBar(
                onBackClick = onBackClick,
                onClickImage = onClickImage
            )

            ProfileImage(
                profileImage = state.profileImageUrl?: "",
                size = 160,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (isOwnProfile) {
                pickImageButton(onImageSelected = onImageSelected)
            }

            UserNick(
                nick = profile.nick,
                isVerified = profile.emailVerified
            )

            if (isOwnProfile && !profile.emailVerified) {
                Button(
                    onClick = onVerifyEmailClick,
                    enabled = !state.isVerifyingEmail,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoxelSurface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .border(
                            width = 2.dp,
                            color = VoxelSecondary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .testTag("btnVerifyEmail")
                ) {
                    Text(
                        text = if (state.isVerifyingEmail) "Verificando..." else "Confirmar / Verificar correo",
                        color = VoxelSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isOwnProfile && state.verificationMessage != null) {
                Text(
                    text = state.verificationMessage,
                    color = VoxelSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 6.dp)
                        .testTag("verificationMessage")
                )
            }

            Biography(
                biografia = state.profile?.biografia
            )

            if (!isOwnProfile){
                var textoBoton: String = ""
                if (profile.followed){
                    textoBoton = "siguiendo"
                }else{
                    textoBoton = "seguir"
                }
                ButtonFollow(

                    text = textoBoton,
                    buttonFollowPressed = {
                        buttonFollowPressed()
                        Log.d("seguirUsuario", "El usuario ${state.currentUserId} esta tratando de seguir a ${state.profile.id}")
                    }
                )
            }

            Spacer(modifier = Modifier.height(9.dp))


            StatsPanel(
                followers = profile.followers,
                followed = profile.following,
                onFollowersClick = onFollowersClick,
                onFollowingClick = onFollowingClick
            )

            when {
                state.isReviewsLoading -> com.example.voxel_review.ui.utils.ContentStatus("Cargando reseñas…", loading = true)
                state.reviewsError != null -> com.example.voxel_review.ui.utils.ContentStatus(
                    state.reviewsError, onRetry = onRetryReviews
                )
                else -> GameCards(reviews = reviews, onReviewClick = onReviewClick)
            }

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
            biografia = "",
            emailVerified = true
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


