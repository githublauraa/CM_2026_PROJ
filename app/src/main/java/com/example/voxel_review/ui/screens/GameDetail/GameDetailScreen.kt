package com.example.voxel_review.ui.screens.GameDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import com.example.voxel_review.ui.screens.GameDetail.components.GameDetailContent

/**
 * Contenedor intermedio para la pantalla de detalle que suscribe la UI al ViewModel.
 *
 * @param gameDetailViewModel ViewModel con el estado actual.
 * @param navController Controlador de navegación.
 * @param onBackPressed Acción para regresar.
 * @param onSearchPressed Acción para buscar.
 * @param onWriteReviewPressed Acción para redactar reseña.
 * @param modifier Modificador para personalización.
 */
@Composable
fun GameDetailScreen(
    gameDetailViewModel: GameDetailViewModel,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by gameDetailViewModel.uiState.collectAsState()


    GameDetailContent(
        state = state,
        onBackPressed = onBackPressed,
        onSearchPressed = onSearchPressed,
        onWriteReviewPressed = onWriteReviewPressed,
        modifier = modifier
    )
}

@Preview
@Composable
fun GameDetailScreenPreview() {

    GameDetailContent(
        state = GameDetailState(
            game = LocalGameProvider.starfield,
            recommendedGames = LocalGameRecomendedProvider.recommendedGames
        ),
        onBackPressed = {},
        onSearchPressed = {},
        onWriteReviewPressed = {}
    )
}
