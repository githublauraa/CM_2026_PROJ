package com.example.voxel_review.profile

import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus
import com.example.voxel_review.data.info.review.ReviewInfo
import com.example.voxel_review.ui.screens.profile.calculatePlayerStatistics
import org.junit.Assert.*
import org.junit.Test

class PlayerStatisticsTest {
    private fun review(id: String, rating: Float = 4f, date: String = "2026-11-01T02:00:00Z") = ReviewInfo(
        idResenia = id, imagenJuego = "", tituloJuego = "", desarrollador = "", tituloDescripcion = "",
        descripcion = "", ratingGeneral = rating, ratingJugabilidad = rating, ratingGraficos = rating,
        ratingHistoria = rating, imagenUsuario = "", videoGameId = id, createdAt = date
    )
    private fun entry(id: String, status: LibraryStatus) = LibraryGameInfo(id, "", "", "", status, 0)

    @Test fun `empty player has zero totals and no invented average`() {
        val stats = calculatePlayerStatistics(emptyList(), emptyList())
        assertNull(stats.averageRating)
        assertEquals(List(6) { 0 }, stats.ratingCounts)
        assertEquals(0, stats.libraryCounts.values.sum())
    }

    @Test fun `ratings preserve fractional average and exclude corrupt values`() {
        val stats = calculatePlayerStatistics(listOf(review("a", 0f), review("b", 3.67f), review("c", 5f), review("d", Float.NaN), review("e", 7f)), emptyList())
        assertEquals(5, stats.reviewCount)
        assertEquals((0.0 + 3.67f.toDouble() + 5.0) / 3, stats.averageRating!!, 0.00001)
        assertEquals(listOf(1, 0, 0, 0, 1, 1), stats.ratingCounts)
    }

    @Test fun `collection totals include every status`() {
        val stats = calculatePlayerStatistics(emptyList(), listOf(
            entry("a", LibraryStatus.COMPLETED), entry("b", LibraryStatus.PLAYING),
            entry("c", LibraryStatus.COMPLETED), entry("d", LibraryStatus.WANT_TO_PLAY)
        ))
        assertEquals(2, stats.libraryCounts[LibraryStatus.COMPLETED])
        assertEquals(1, stats.libraryCounts[LibraryStatus.PLAYING])
        assertEquals(1, stats.libraryCounts[LibraryStatus.WANT_TO_PLAY])
    }
}
