# Protección contra spoilers

El formulario de crear y editar reseñas incluye «Contiene spoilers». El estado se conserva en el campo booleano `containsSpoilers` del documento de la reseña. Al editar se carga el valor guardado y puede activarse o desactivarse. Las reseñas existentes sin ese campo se consideran sin spoilers.

Las reseñas marcadas muestran un aviso y el botón «Mostrar reseña» en lugar del contenido. Tras revelarlo, el lector puede tocar «Ocultar reseña». El texto oculto no forma parte del árbol de accesibilidad. La decisión de mostrarlo es local a cada vista; no cambia el documento de Firebase y se restablece al cambiar de reseña o contenido.

La protección se aplica a detalle del juego, reseña completa, sección Siguiendo y vista previa del mapa. Las portadas del perfil no muestran texto de reseñas. Al compartir una reseña marcada se incluye el aviso y se omite su contenido, para evitar revelar el spoiler a los destinatarios.

Los avisos, la casilla y los botones tienen traducciones en español, inglés y portugués de Brasil. No se requieren nuevas dependencias ni una migración de documentos existentes.

## Verificación

- Pruebas unitarias: envío de la marca al crear; restauración y desactivación al editar; compatibilidad con reseñas anteriores.
- Pruebas de interfaz aisladas: contenido oculto inicialmente, mostrar y volver a ocultar, restablecimiento al cambiar de reseña o texto, y contenido sin spoilers visible directamente.
- Las pruebas no escriben reseñas en Firebase. La persistencia remota se integra en las rutas de creación y actualización existentes.
