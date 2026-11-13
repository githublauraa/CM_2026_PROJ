package com.example.voxel_review.data.dtos

import com.example.voxel_review.data.info.library.LibraryGameInfo
import com.example.voxel_review.data.info.library.LibraryStatus

/**
 * DTO de Firestore para los documentos en `users/{userId}/library/{videoGameId}`.
 */
data class LibraryEntryDto(
    val videoGameId: String = "",
    val gameName: String = "",
    val gameImageUrl: String? = null,
    val gameDeveloper: String = "",
    val status: String = LibraryStatus.WANT_TO_PLAY.name,
    val updatedAt: Long = 0L
)

fun LibraryEntryDto.toLibraryGameInfo(): LibraryGameInfo? {
    val parsedStatus = LibraryStatus.fromNameOrNull(status) ?: return null
    return LibraryGameInfo(
        videoGameId = videoGameId,
        gameName = gameName,
        gameImageUrl = gameImageUrl ?: "",
        gameDeveloper = gameDeveloper,
        status = parsedStatus,
        updatedAt = updatedAt
    )
}
