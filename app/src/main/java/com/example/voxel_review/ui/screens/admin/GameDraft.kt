package com.example.voxel_review.ui.screens.admin

import com.example.voxel_review.data.dtos.VideoGameDto
import java.net.URI
import java.time.LocalDate

/** Editable fields; limits mirror the catalog Firestore rules. */
data class GameDraft(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val developer: String = "",
    val platforms: String = "",
    val releaseDate: String = "",
    val imageUrl: String = "",
    val genres: String = "",
    val archived: Boolean = false
) {
    fun isValid(): Boolean {
        val url = runCatching { URI(imageUrl.trim()) }.getOrNull()
        return name.trim().length in 1..120 && description.trim().length in 1..5000 &&
            developer.trim().length in 1..120 && platforms.trim().length in 1..200 &&
            releaseDate.matches(Regex("[0-9]{4}-[0-9]{2}-[0-9]{2}")) &&
            runCatching { LocalDate.parse(releaseDate) }.isSuccess &&
            imageUrl.trim().length <= 2048 && url?.scheme == "https" && !url.host.isNullOrBlank() &&
            genreList().size in 1..10 && genreList().all { it.length in 1..60 }
    }

    private fun genreList() = genres.split(',').map { it.trim() }.filter { it.isNotEmpty() }.distinct()

    fun toGame() = VideoGameDto(id, name.trim(), description.trim(), developer.trim(),
        platforms.trim(), releaseDate, imageUrl.trim(), genreList(), archived)

    companion object {
        fun from(game: VideoGameDto) = GameDraft(game.videoGameId, game.name, game.description,
            game.developer, game.platform, game.releaseDate, game.imageUrl.orEmpty(),
            game.genres.joinToString(", "), game.archived)
    }
}
