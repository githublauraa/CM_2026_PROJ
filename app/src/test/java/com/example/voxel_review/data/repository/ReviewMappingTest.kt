package com.example.voxel_review.data.repository

import com.example.voxel_review.data.dtos.ReviewDto
import com.example.voxel_review.data.dtos.UserDto
import com.example.voxel_review.data.dtos.toReviewInfo
import org.junit.Assert.*
import org.junit.Test

class ReviewMappingTest {
    @Test fun preservesRatingsUsedByNovedades() {
        val review = ReviewDto(gameplayRating = 3, graphicsRating = 4, storyRating = 5).toReviewInfo()
        assertEquals(4f, review.ratingGeneral, 0.0001f)
        assertEquals(3f, review.ratingJugabilidad, 0f)
    }
    @Test fun preservesIdentityContentAndLikesInDetail() {
        val review = ReviewDto(reviewId = "review", userId = "author", videoGameId = "game",
            content = "Comentario", cantidadLikes = 7, likedByCurrentUser = true,
            user = UserDto(username = "Usuario")).toReviewInfo()
        assertEquals("review", review.idResenia)
        assertEquals("author", review.userId)
        assertEquals("game", review.videoGameId)
        assertEquals("Comentario", review.descripcion)
        assertEquals(7, review.cantidadLikes)
        assertTrue(review.likedByCurrentUser)
    }
    @Test fun zeroScoresRemainValid() {
        assertEquals(0f, ReviewDto().toReviewInfo().ratingGeneral, 0f)
    }
}
