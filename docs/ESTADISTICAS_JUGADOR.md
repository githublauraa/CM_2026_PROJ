# Estadísticas del jugador

Acceso: Perfil → Estadísticas. Disponible también al visitar un perfil público, con los mismos datos de reseñas y colección que ya muestra ese perfil.

- Resumen: reseñas publicadas, promedio de notas válidas (0–5) y juegos completados.
- Colección: barras para Por jugar, Jugando y Completado, independientes del filtro de la pestaña Colección.
- Puntuaciones: seis grupos de estrellas (0–5). Las notas decimales se agrupan por la estrella entera más cercana; el promedio conserva los decimales.

## Validación

Se agregaron pruebas unitarias para perfil vacío, promedio y agrupación de notas decimales, y conteos de todos los estados de la colección. La interfaz utiliza las traducciones de español, inglés y portugués existentes, y gráficos de barras de Compose sin nuevas dependencias.
