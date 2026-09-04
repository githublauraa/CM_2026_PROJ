package com.example.voxel_review.ui.screens.GameDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

/**
 * Punto de entrada para la ruta de detalle de juego. Se encarga del ciclo de vida y carga de datos.
 *
 * @param gameDetailViewModel ViewModel que gestiona los datos de detalle.
 * @param game Juego que se cargará en la vista.
 * @param recommendedGames Lista de juegos recomendados asociados.
 * @param navController Controlador de navegación.
 * @param modifier Modificador para el contenedor.
 * @param onBackPressed Callback para evento de regreso.
 * @param onSearchPressed Callback para apertura de buscador.
 * @param onWriteReviewPressed Callback para creación de reseña.
 */
@Composable
fun GameDetailContent(
    gameDetailViewModel: GameDetailViewModel,
    gameIndex: Int,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit
) {

    LaunchedEffect(gameIndex) {
        gameDetailViewModel.loadGame(gameIndex)
    }

    GameDetailScreen(
        gameDetailViewModel = gameDetailViewModel,
        onBackPressed = onBackPressed,
        onSearchPressed = onSearchPressed,
        onWriteReviewPressed = onWriteReviewPressed,
        modifier = modifier
    )
}