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
