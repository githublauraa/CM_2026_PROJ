package com.example.voxel_review.data.review


object LocalReviewProvider {

    val reviews = listOf(
        ReviewInfo(
            imagenJuego = "URL_IMAGEN_JUEGO",
            tituloJuego = "Chrono Sphere",
            desarrollador = "Voxel Studios",
            tituloDescripcion = "Una obra maestra",
            descripcion = "Una experiencia única que combina " +
                    "una historia profunda y excelente jugabilidad.",
            ratingGeneral = 4.2f,
            ratingJugabilidad = 4.5f,
            ratingGraficos = 4.0f,
            ratingHistoria = 4.1f,
            imagenUsuario = "URL_IMAGEN_USUARIO",
            usernameReview = "jugador1",
            comentarioReview = "Una experiencia única.",
            ratingUsuario = 4
        ),

        ReviewInfo(
            imagenJuego = "URL_IMAGEN_JUEGO",
            tituloJuego = "Shadow Realm",
            desarrollador = "Desarrollado por Dark Pixel",
            tituloDescripcion = "Una gran aventura",
            descripcion = "Un juego con una ambientación sobresaliente y un sistema de combate muy entretenido.",
            ratingGeneral = 4.5f,
            ratingJugabilidad = 4.7f,
            ratingGraficos = 4.4f,
            ratingHistoria = 4.3f,
            imagenUsuario = "URL_IMAGEN_USUARIO",
            usernameReview = "jugador2",
            comentarioReview = "Me encantó la historia y los gráficos.",
            ratingUsuario = 5
        )
    )
}
