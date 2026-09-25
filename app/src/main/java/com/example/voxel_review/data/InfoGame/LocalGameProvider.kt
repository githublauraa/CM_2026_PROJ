package com.example.voxel_review.data.InfoGame

import com.example.voxel_review.data.InfoDiscover.LocalGenreProvider

object LocalGameProvider {

    val eldenRing = GameDetailInfo(
        banner = "URL_BANNER_ELDEN_RING",
        nombre = "Elden Ring",
        descripcion = "Un vasto mundo abierto creado por FromSoftware y George R. R. Martin. " +
                "Explora las Tierras Intermedias, enfréntate a jefes brutales y descubre " +
                "los secretos del Círculo de Elden.",
        desarrollador = "FromSoftware",
        lanzamiento = "Feb 2022",
        generos = listOf(
            LocalGenreProvider.generos.first { it.id == "2" },
            LocalGenreProvider.generos.first { it.id == "3" },
            LocalGenreProvider.generos.first { it.id == "4" }
        ),
        imagen = "URL_IMAGEN_ELDEN_RING"
    )

    val starfield = GameDetailInfo(
        banner = "URL_BANNER_STARFIELD",
        nombre = "Starfield",
        descripcion = "Explora un vasto universo de Bethesda Game Studios. " +
                "Crea tu personaje, navega por mil planetas y descubre misterios " +
                "cósmicos en esta aventura espacial de proporciones épicas.",
        desarrollador = "Bethesda",
        lanzamiento = "Oct 2023",
        generos = listOf(
            LocalGenreProvider.generos.first { it.id == "1" },
            LocalGenreProvider.generos.first { it.id == "2" },
            LocalGenreProvider.generos.first { it.id == "3" },
            LocalGenreProvider.generos.first { it.id == "4" },
            LocalGenreProvider.generos.first { it.id == "5" }
        ),
        imagen = "URL_IMAGEN_STARFIELD"
    )

    val baldursGate3 = GameDetailInfo(
        banner = "URL_BANNER_BALDURS_GATE_3",
        nombre = "Baldur's Gate 3",
        descripcion = "Un RPG por turnos de Larian Studios ambientado en el universo de " +
                "Dungeons & Dragons. Reúne tu grupo y decide el destino de las Tierras " +
                "Olvidadas en una historia llena de decisiones.",
        desarrollador = "Larian Studios",
        lanzamiento = "Ago 2023",
        generos = listOf(
            LocalGenreProvider.generos.first { it.id == "2" },
            LocalGenreProvider.generos.first { it.id == "5" }
        ),
        imagen = "URL_IMAGEN_BALDURS_GATE_3"
    )

    val games = listOf(
        eldenRing,
        starfield,
        baldursGate3
    )
}