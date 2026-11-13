package com.example.voxel_review.data.info.library

/**
 * Estados posibles de un videojuego dentro de la colección personal (backlog) del usuario.
 */
enum class LibraryStatus(val label: String) {
    WANT_TO_PLAY("Por jugar"),
    PLAYING("Jugando"),
    COMPLETED("Completado");

    companion object {
        fun fromNameOrNull(value: String?): LibraryStatus? =
            entries.firstOrNull { it.name == value }
    }
}

/**
 * Modelo de dominio/UI que representa un videojuego guardado en la colección de un usuario.
 */
data class LibraryGameInfo(
    val videoGameId: String,
    val gameName: String,
    val gameImageUrl: String,
    val gameDeveloper: String,
    val status: LibraryStatus,
    val updatedAt: Long
)
