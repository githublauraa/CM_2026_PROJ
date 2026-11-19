package com.example.voxel_review.repository

import com.example.voxel_review.data.dtos.ReviewDto
import com.example.voxel_review.data.dtos.newestFirst
import com.example.voxel_review.data.dataSource.impl.firestore.ReviewFirebaseDataSourceImpl
import com.example.voxel_review.data.repository.AuthRepository
import com.example.voxel_review.data.repository.ReviewRepository
import com.example.voxel_review.data.localization.AppStrings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class ReviewOrderingTest {
    private val old = ReviewDto(reviewId = "z-old", createdAt = "2026-11-01T12:00:00Z")
    private val recent = ReviewDto(reviewId = "a-new", createdAt = "2026-11-19T12:00:00Z")

    @Test fun `publication date wins over document order and invalid dates come last`() {
        val reviews = listOf(ReviewDto(reviewId = "missing"), old, ReviewDto(reviewId = "invalid", createdAt = "bad"), recent)
        assertEquals(listOf("a-new", "z-old", "invalid", "missing"), reviews.newestFirst().map { it.reviewId })
        assertTrue(emptyList<ReviewDto>().newestFirst().isEmpty())
    }

    @Test fun `fractional seconds and offsets are compared chronologically`() {
        val reviews = listOf(
            ReviewDto(reviewId = "earlier", createdAt = "2026-11-19T12:00:00Z"),
            ReviewDto(reviewId = "later", createdAt = "2026-11-19T07:00:00.100-05:00")
        )
        assertEquals("later", reviews.newestFirst().first().reviewId)
    }

    @Test fun `game reviews and default selection use newest but explicit selection is preserved`() = runTest {
        val source = mockk<ReviewFirebaseDataSourceImpl>()
        val auth = mockk<AuthRepository>()
        every { auth.currentUser } returns null
        val repository = ReviewRepository(source, auth, mockk<AppStrings>(relaxed = true))
        coEvery { source.listenReview("forza") } returns flowOf(listOf(old, recent))
        every { source.listenSelectedReview("forza", "") } returns flowOf(listOf(old, recent))
        every { source.listenSelectedReview("forza", old.reviewId) } returns flowOf(listOf(old, recent))
        assertEquals(listOf("a-new", "z-old"), repository.getReviewsLive("forza").first().map { it.idResenia })
        assertEquals("a-new", repository.getSelectedReviewLive("forza", "").first()?.idResenia)
        assertEquals("z-old", repository.getSelectedReviewLive("forza", old.reviewId).first()?.idResenia)
    }
}
