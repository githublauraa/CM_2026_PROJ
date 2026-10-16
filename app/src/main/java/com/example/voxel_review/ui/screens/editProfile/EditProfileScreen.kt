package com.example.voxel_review.ui.screens.editProfile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.voxel_review.R
import com.example.voxel_review.ui.screens.crearCuenta.components.FormularioCreateAccount
import com.example.voxel_review.ui.theme.VoxelPrimary
import com.example.voxel_review.ui.theme.VoxelSurface
import com.example.voxel_review.ui.theme.White
import com.example.voxel_review.ui.theme.VoxelTextSecondary
import com.example.voxel_review.ui.utils.FondoPantalla
import androidx.compose.runtime.LaunchedEffect
@Composable
fun EditProfileContent(
    state: EditProfileState,
    onBioChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Editar perfil",
                color = White,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(7.dp)
            )

            // CAMPO BIOGRAFÍA
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Biografía",
                    color = VoxelTextSecondary,
                    fontWeight = FontWeight.Bold
                )

                if (state.isProfileLoaded) {
                    FormularioCreateAccount(
                        value = state.bio,
                        icono = R.drawable.bio,
                        placeholder = "Biografía",
                        onValueChange = onBioChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    )
                } else if (state.isProfileLoading) {
                    Text(text = "Cargando perfil...", color = VoxelTextSecondary)
                }

                // BOTÓN GUARDAR CAMBIOS
                Button(
                    onClick = onSaveClick,
                    enabled = state.isProfileLoaded && !state.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = VoxelSurface,
                        disabledContainerColor = VoxelSurface
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                        .fillMaxWidth(0.9f)
                        .height(55.dp)
                        .border(
                            width = 2.dp,
                            color = VoxelPrimary,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = if (state.isLoading) {
                            "Guardando..."
                        } else {
                            "Guardar cambios"
                        },
                        color = VoxelPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // MENSAJE DE ÉXITO
                if (state.isSaved) {
                    Text(
                        text = "Biografía actualizada correctamente",
                        color = VoxelPrimary,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                // MENSAJE DE ERROR
                state.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = White,
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
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
        onSaveClick = editProfileViewModel::saveBio,
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EditProfileContentPreview() {
    EditProfileContent(
        state = EditProfileState(
            bio = "Me encantan los videojuegos RPG y de aventura.",
            isProfileLoaded = true
        ),
        onBioChange = {},
        onSaveClick = {}
    )
}
