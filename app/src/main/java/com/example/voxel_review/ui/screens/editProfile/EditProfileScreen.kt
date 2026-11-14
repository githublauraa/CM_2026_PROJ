package com.example.voxel_review.ui.screens.editProfile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.profile.components.Pfp
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelTextPrimary
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.theme.errorDark
import com.example.voxel_review.ui.utils.AuthScreenLayout
import com.example.voxel_review.ui.utils.AuthSubmitButton
import com.example.voxel_review.ui.utils.ScreenHeader
import com.example.voxel_review.ui.utils.authFieldColors
import com.example.voxel_review.ui.utils.authFieldVisibility
import com.example.voxel_review.ui.utils.authFormBackground

@Composable
fun EditProfileContent(
    state: EditProfileState,
    onBioChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val fieldShape = RoundedCornerShape(14.dp)
    val fieldColors = authFieldColors()

    AuthScreenLayout(modifier) {
        ScreenHeader(stringResource(R.string.edit_profile_title), onBackClick)

        if (state.isProfileLoaded) {
            Row(
                modifier = Modifier.authFormBackground(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Pfp(
                    profileImage = state.profileImageUrl,
                    modifier = Modifier
                        .size(64.dp)
                        .border(1.dp, VoxelPrimary, CircleShape)
                        .padding(3.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = state.username.ifBlank {
                            stringResource(R.string.register_username)
                        },
                        color = VoxelTextPrimary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.edit_profile_visibility),
                        color = VoxelTextSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        Column(
            modifier = Modifier.authFormBackground(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (state.isProfileLoaded) {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = onUsernameChange,
                    modifier = authFieldVisibility().fillMaxWidth(),
                    label = { Text(stringResource(R.string.register_username)) },
                    readOnly = state.isLoading,
                    singleLine = true,
                    shape = fieldShape,
                    colors = fieldColors
                )
                OutlinedTextField(
                    value = state.bio,
                    onValueChange = onBioChange,
                    modifier = authFieldVisibility().fillMaxWidth(),
                    label = { Text(stringResource(R.string.register_bio)) },
                    readOnly = state.isLoading,
                    minLines = 2,
                    maxLines = 4,
                    shape = fieldShape,
                    colors = fieldColors
                )
            } else if (state.isProfileLoading) {
                Text(
                    text = stringResource(R.string.edit_profile_loading),
                    color = VoxelTextSecondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            AuthSubmitButton(
                label = stringResource(R.string.edit_profile_save),
                loading = state.isLoading,
                enabled = state.isProfileLoaded,
                onClick = {
                    focusManager.clearFocus()
                    onSaveClick()
                }
            )
            state.errorMessage?.let { error ->
                Text(
                    text = error,
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    color = errorDark,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun EditProfileScreen(
    editProfileViewModel: EditProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state by editProfileViewModel.uiState.collectAsState()

    LaunchedEffect(editProfileViewModel) {
        editProfileViewModel.loadProfile()
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onBackClick()
        }
    }

    EditProfileContent(
        state = state,
        onBioChange = editProfileViewModel::updateBio,
        onSaveClick = editProfileViewModel::saveProfile,
        onUsernameChange = editProfileViewModel::updateUsername,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun EditProfileContentPreview() {
    EditProfileContent(
        state = EditProfileState(
            username = "PixelRPG",
            bio = "Me encantan los videojuegos RPG y de aventura.",
            isProfileLoaded = true
        ),
        onUsernameChange = {},
        onBioChange = {},
        onBackClick = {},
        onSaveClick = {}
    )
}
