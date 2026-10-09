
package com.example.voxel_review.ui.screens.profile

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.voxel_review.data.profile.ProfileInfo
import com.example.voxel_review.ui.screens.profile.components.*
import com.example.voxel_review.ui.theme.VoxelBackground
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.verticalScroll
import com.example.voxel_review.R
import com.example.voxel_review.data.profile.ProfileInfo
import com.example.voxel_review.data.review.ReviewInfo
import android.net.Uri
import com.example.voxel_review.ui.utils.ProfileImage

@Composable
fun ProfileScreen(
    profileId: String,
    profileViewModel: ProfileViewModel,
    onBackClick: () -> Unit,
    onClickImage: () -> Unit,
    buttonLogOutPressed: () -> Unit,
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    val state by profileViewModel.uiState.collectAsState()

    LaunchedEffect(profileId) {
        profileViewModel.getProfileById(profileId)
        profileViewModel.getUserReviews(profileId)
    }

    ProfileContent(
        state = state,
        onImageSelected = {
            profileViewModel.uploadImageFireBase(it)
        },
        onBackClick = onBackClick,
        onClickImage = onClickImage,
        onReviewClick = onReviewClick,
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
    onReviewClick: (ReviewInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    val profile = state.profile ?: return
    val reviews = state.reviews ?: emptyList()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(VoxelBackground)
    ) {

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 70.dp)
        ) {

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TopBar(
                        onBackClick = onBackClick,
                        onClickImage = onClickImage
                    )

                    ProfileImage(
                        profileImage = state.profileImageUrl ?: "",
                        size = 200,
                        modifier = Modifier.padding(16.dp)
                    )

                    pickImageButton(
                        onImageSelected = onImageSelected
                    )

                    UserNick(profile.nick)

                    Location()

            GameCards(
                reviews = reviews,
                onReviewClick = onReviewClick
            )

                    GameCards(reviews)

                    EditButton()

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    ButtonLogOut(
                        buttonLogOutPressed = buttonLogOutPressed
                    )
                }
            }

            item {
                ProfileReviews(
                    reviews = reviews,
                    onEditReview = onEditReview,
                    onDeleteReview = onDeleteReview
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
        onReviewClick = {}
    )
}
