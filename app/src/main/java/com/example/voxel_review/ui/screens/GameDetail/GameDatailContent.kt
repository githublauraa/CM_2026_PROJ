package com.example.voxel_review.ui.screens.GameDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.voxel_review.data.info.review.ReviewInfo

/**
 * Punto de entrada para la ruta de detalle de juego. Se encarga del ciclo de vida y carga de datos.
 *
 * @param gameDetailViewModel ViewModel que gestiona los datos de detalle.
 * @param modifier Modificador para el contenedor.
 * @param onBackPressed Callback para evento de regreso.
 * @param onSearchPressed Callback para apertura de buscador.
 * @param onWriteReviewPressed Callback para creación de reseña.
 */
@Composable
fun GameDetailContent(
    onReviewProfileImageCliked:(String) -> Unit,
    gameDetailViewModel: GameDetailViewModel,
    gameId: String,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit,
    onReviewClick: (ReviewInfo) -> Unit = {}
) {

    LaunchedEffect(gameId) {
        gameDetailViewModel.loadGame(gameId)
    }

    GameDetailScreen(
        onReviewProfileImageCliked = onReviewProfileImageCliked,
        gameDetailViewModel = gameDetailViewModel,
        onBackPressed = onBackPressed,
        onSearchPressed = onSearchPressed,
        onWriteReviewPressed = onWriteReviewPressed,
        onReviewClick = onReviewClick,
        modifier = modifier
    )
}