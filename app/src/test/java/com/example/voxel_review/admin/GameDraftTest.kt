package com.example.voxel_review.admin

import com.example.voxel_review.ui.screens.admin.GameDraft
import org.junit.Assert.*
import org.junit.Test

class GameDraftTest {
    private val valid = GameDraft(name = " Celeste ", description = "Plataformas", developer = "Maddy",
        platforms = "PC, Switch", releaseDate = "2018-01-25", imageUrl = "https://example.com/cover.jpg", genres = "Indie, Plataformas, Indie")

    @Test fun `accepts complete game and normalizes fields`() {
        assertTrue(valid.isValid())
        assertEquals("Celeste", valid.toGame().name)
        assertEquals(listOf("Indie", "Plataformas"), valid.toGame().genres)
        assertEquals(valid.toGame(), GameDraft.from(valid.toGame()).toGame())
    }
    @Test fun `rejects impossible dates and missing or oversized fields`() {
        assertFalse(valid.copy(releaseDate = "2025-02-29").isValid())
        assertFalse(valid.copy(releaseDate = "2026").isValid())
        assertTrue(valid.copy(releaseDate = "2024-02-29").isValid())
        assertFalse(valid.copy(name = " ").isValid())
        assertFalse(valid.copy(description = "a".repeat(5001)).isValid())
        assertFalse(valid.copy(developer = "").isValid())
        assertFalse(valid.copy(platforms = "").isValid())
        assertFalse(valid.copy(genres = ", ,").isValid())
        assertFalse(valid.copy(genres = (1..11).joinToString(",")).isValid())
    }
    @Test fun `requires usable HTTPS cover URL`() {
        listOf("", "http://example.com/a.jpg", "https:", "https:///cover.jpg", "https://bad host/a").forEach {
            assertFalse(it, valid.copy(imageUrl = it).isValid())
        }
    }
}
