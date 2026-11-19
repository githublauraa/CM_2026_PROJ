package com.example.voxel_review.viewModels

import android.location.Location
import com.example.voxel_review.data.dataSource.services.isRecentReviewLocation
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class ReviewLocationValidationTest {
    private val now = 120_000_000_000L
    private fun location(ageSeconds: Long, latitude: Double = 4.6, longitude: Double = -74.08): Location = mockk {
        every { this@mockk.latitude } returns latitude
        every { this@mockk.longitude } returns longitude
        every { elapsedRealtimeNanos } returns now - ageSeconds * 1_000_000_000L
    }
    @Test fun `accepts recent cached fix from before publish tap`() {
        assertTrue(isRecentReviewLocation(location(30), now))
        assertTrue(isRecentReviewLocation(location(60), now))
    }
    @Test fun `rejects stale future and invalid coordinates`() {
        assertFalse(isRecentReviewLocation(location(61), now))
        assertFalse(isRecentReviewLocation(location(-1), now))
        assertFalse(isRecentReviewLocation(location(1, 95.0), now))
        assertFalse(isRecentReviewLocation(location(1, longitude = Double.NaN), now))
    }
}
