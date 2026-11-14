package com.example.voxel_review.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import com.example.voxel_review.ui.theme.VoxelSurface
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import com.google.firebase.auth.FirebaseAuth
import com.example.voxel_review.data.info.profile.ProfileInfo
import com.example.voxel_review.data.info.review.ReviewInfo
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.voxel_review.ui.theme.onSurfaceVariantDark
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
    onGameClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    val state by profileViewModel.uiState.collectAsState()

    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
    val isOwnProfile = currentUserId != null && profileId == currentUserId

    LaunchedEffect(profileId) {
        profileViewModel.getProfileById(profileId)
        profileViewModel.getUserReviews(profileId)
        profileViewModel.getUserLibrary(profileId)
    }

    ProfileContent(
        state = state,
        onRetry = { profileViewModel.getProfileById(profileId) },
        onRetryReviews = { profileViewModel.getUserReviews(profileId) },
        onRetryLibrary = { profileViewModel.getUserLibrary(profileId) },
        onImageSelected = { profileViewModel.uploadImageFireBase(it) },
        onBackClick = onBackClick,
        onClickImage = onClickImage,
        onReviewClick = onReviewClick,
        onGameClick = onGameClick,
        onSelectTab = profileViewModel::selectProfileTab,
        onSelectLibraryFilter = profileViewModel::selectLibraryFilter,
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
    onGameClick: (String) -> Unit = {},
    onSelectTab: (ProfileContentTab) -> Unit = {},
    onSelectLibraryFilter: (com.example.voxel_review.data.info.library.LibraryStatus?) -> Unit = {},
    onRetry: () -> Unit = {},
    onRetryReviews: () -> Unit = {},
    onRetryLibrary: () -> Unit = {},
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
            .background(
                Brush.verticalGradient(0f to VoxelSurface, 0.34f to VoxelBackground)
            )
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

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ProfileImage(
                    profileImage = state.profileImageUrl ?: profile.pfp,
                    size = 100
                )
                UserNick(nick = profile.nick, isVerified = profile.emailVerified)
                Biography(
                    biografia = profile.biografia,
                    modifier = Modifier.fillMaxWidth()
                )
                StatsPanel(
                    followers = profile.followers,
                    followed = profile.following,
                    onFollowersClick = onFollowersClick,
                    onFollowingClick = onFollowingClick,
                    modifier = Modifier.padding(top = 4.dp)
                )
                if (isOwnProfile) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EditButton(onEditProfileClick = onEditProfileClick, modifier = Modifier.weight(1f))
                        pickImageButton(onImageSelected = onImageSelected, modifier = Modifier.weight(1f))
                    }
                } else {
                    ButtonFollow(
                        text = if (profile.followed) "siguiendo" else "seguir",
                        buttonFollowPressed = buttonFollowPressed
                    )
                }
                if (isOwnProfile && !profile.emailVerified) {
                    OutlinedButton(
                        onClick = onVerifyEmailClick,
                        enabled = !state.isVerifyingEmail,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = onSurfaceVariantDark),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.testTag("btnVerifyEmail")
                    ) {
                        Text(
                            text = if (state.isVerifyingEmail) "Verificando..." else "Confirmar / Verificar correo",
                            color = onSurfaceVariantDark,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (isOwnProfile && state.verificationMessage != null) {
                    Text(
                        text = state.verificationMessage,
                        color = onSurfaceVariantDark,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(top = 6.dp)
                            .testTag("verificationMessage")
                    )
                }
            }

            ProfileTabChips(
                selectedTab = state.selectedTab,
                onSelectTab = onSelectTab
            )

            when (state.selectedTab) {
                ProfileContentTab.REVIEWS -> {
                    when {
                        state.isReviewsLoading -> com.example.voxel_review.ui.utils.ContentStatus("Cargando reseñas…", loading = true)
                        state.reviewsError != null -> com.example.voxel_review.ui.utils.ContentStatus(
                            state.reviewsError, onRetry = onRetryReviews
                        )
                        else -> GameCards(reviews = reviews, onReviewClick = onReviewClick)
                    }
                }
                ProfileContentTab.LIBRARY -> {
                    ProfileLibrarySection(
                        games = state.filteredLibraryGames,
                        selectedFilter = state.selectedLibraryFilter,
                        isLoading = state.isLibraryLoading,
                        errorMessage = state.libraryError,
                        onSelectFilter = onSelectLibraryFilter,
                        onGameClick = onGameClick,
                        onRetry = onRetryLibrary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isOwnProfile) {
                ButtonLogOut(
                    buttonLogOutPressed = buttonLogOutPressed,
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
                )
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
            biografia = "Me gustan los RPG y los juegos de aventura.",
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


