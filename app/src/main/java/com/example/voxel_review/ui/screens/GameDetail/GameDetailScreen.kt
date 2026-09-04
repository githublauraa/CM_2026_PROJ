package com.example.voxel_review.ui.screens.GameDetail

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import com.example.voxel_review.ui.screens.GameDetail.components.GameDetailContent

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
fun GameDetailRoute(
    gameDetailViewModel: GameDetailViewModel,
    game: GameDetailInfo,
    recommendedGames: List<GameDetailInfo>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit
) {
    // Carga los detalles del juego en segundo plano al iniciar la pantalla.
    // Se reinicia automáticamente si cambian 'game' o 'recommendedGames'
    LaunchedEffect(game, recommendedGames) {
        gameDetailViewModel.loadGame(
            game = game,
            recommendedGames = recommendedGames
        )
    }

    GameDetailScreen(
        gameDetailViewModel = gameDetailViewModel,
        navController = navController,
        onBackPressed = onBackPressed,
        onSearchPressed = onSearchPressed,
        onWriteReviewPressed = onWriteReviewPressed,
        modifier = modifier
    )
}

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
    navController: NavHostController,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val state by gameDetailViewModel.uiState.collectAsState()

    GameDetailContent(
        state = state,
        navController = navController,
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
        navController = rememberNavController(),
        onBackPressed = {},
        onSearchPressed = {},
        onWriteReviewPressed = {}
    )
}
