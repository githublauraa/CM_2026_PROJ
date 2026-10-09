package com.example.voxel_review.ui.screens.review

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.voxel_review.ui.screens.review.components.ReviewDetailContent
import com.example.voxel_review.ui.screens.review.components.ReviewDetailTopBar
import com.example.voxel_review.ui.utils.FondoPantalla

@Composable
fun ReviewDetailScreenContent(
    state: ReviewDetailState,
    onClickReview: () -> Unit,
    onBackClick: () -> Unit,
    onCommentReplyClicked: (String) -> Unit = {},
    onCommentDetailClicked: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        FondoPantalla(
            modifier = Modifier.fillMaxSize()
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // 1. Barra superior de navegación
            item {
                ReviewDetailTopBar(
                    onBackClick = onBackClick
                )
            }

            // 2. Contenido principal de la reseña
            item {
                ReviewDetailContent(
                    state = state,
                    onClickReview = onClickReview
                )
            }

            // 3. Barra de acciones (Comentar, Like, Compartir)
            item {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                ReviewActionBar(
                    onComment = {
                        state.review?.id?.let { id -> onCommentReplyClicked(id) }
                    },
                    onLike = { /* Lógica para dar me gusta */ },
                    onShare = { /* Lógica para compartir */ },
                    isLiked = state.review?.isLiked ?: false
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "Respuestas y comentarios",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }

            // 4. Sección de comentarios / respuestas
            if (state.comments.isEmpty()) {
                item {
                    Text(
                        text = "Aún no hay comentarios. ¡Sé el primero en opinar!",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            } else {
                items(state.comments.size) { index ->
                    val comment = state.comments[index]

                    CommentRow(
                        comment = comment,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCommentDetailClicked(comment.id) }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    HorizontalDivider(thickness = 0.5.dp)
                }
            }
        }
    }
}