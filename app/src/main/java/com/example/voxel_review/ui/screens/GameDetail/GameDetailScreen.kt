package com.example.voxel_review.ui.screens.GameDetail

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.voxel_review.data.InfoGame.GameDetailInfo
import com.example.voxel_review.data.InfoGame.GameDetailState
import com.example.voxel_review.data.InfoGame.LocalGameProvider
import com.example.voxel_review.data.InfoGame.LocalGameRecomendedProvider
import com.example.voxel_review.ui.screens.GameDetail.components.GameDetailContent
@SuppressLint("RememberReturnType")
@Composable
fun GameDetailRoute(
    gameDetailViewModel: GameDetailViewModel,
    game: GameDetailInfo,
    recommendedGames: List<GameDetailInfo>,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit = {},
) {

    val state by gameDetailViewModel.uiState.collectAsState()

    remember(game, recommendedGames) {
        gameDetailViewModel.loadGame(game, recommendedGames)
    }

    GameDetailScreen(
        state = state,
        navController = navController,
        onBackPressed = onBackPressed,
        onSearchPressed = onSearchPressed,
        onWriteReviewPressed = onWriteReviewPressed,
        modifier = modifier
    )
}
@Composable
fun GameDetailScreen(
    state: GameDetailState,
    navController: NavHostController,
    onBackPressed: () -> Unit,
    onSearchPressed: () -> Unit,
    onWriteReviewPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    GameDetailContent(
        state = state,
        navController = navController,
        onBackPressed = onBackPressed,
        onSearchPressed = onSearchPressed,
        onWriteReviewPressed = onWriteReviewPressed
    )
}

@Preview(
    showBackground = false,
    showSystemUi = false,
    name = "Game Detail Screen"
)
@Composable
fun GameDetailScreenPreview() {

    GameDetailScreen(
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
