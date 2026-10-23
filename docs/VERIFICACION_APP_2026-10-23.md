# Revisión de Voxel Review — 23 de octubre de 2026

## Simplificación de la optimización

- Se retiró `async` de Novedades; se conservan `viewModelScope.launch`, `Result`, `onSuccess` y `onFailure`.
- Se retiró el archivo auxiliar `ReviewRatings.kt` y su agrupación con `groupBy` y `mapValues`.
- El promedio se calcula con `filter`, `map` y `average`, como en el código original.
- Novedades sigue haciendo dos consultas de colecciones, sin descargar autores ni likes de cada reseña. Las dos consultas ahora son secuenciales.
- Reseña completa mantiene un listener de Firebase para un único documento, o la primera reseña del juego. Se conserva el listener general existente utilizado por Detalle del juego.
- No se añadieron dependencias de producción ni se modificó el esquema de Firebase.

## Verificación local

Compilación de producción y pruebas instrumentadas: correcta. Pruebas unitarias: correctas. `lintDebug`: finalizó correctamente; los avisos están en `app/build/reports/lint-results-debug.html`.

## Resultado final

La batería instrumentada final terminó con `OK (6 tests)` en 30,255 segundos. Se ejecutó con la cuenta de prueba existente, sin crear ni eliminar cuentas. Las pruebas unitarias también pasaron. Las seis pruebas agrupan las comprobaciones descritas abajo; no equivalen a una garantía exhaustiva sobre todas las funciones o condiciones de red.

## Comprobaciones realizadas

| Flujo | Resultado y alcance |
|---|---|
| Buscar y limpiar búsqueda | Pasó en prueba instrumentada de interfaz |
| Navegar a Notificaciones y volver | Pasó en prueba instrumentada de interfaz |
| Abrir Perfil, Configuración y volver | Pasó en prueba instrumentada de interfaz |
| Cargar Novedades | Pasó en prueba instrumentada de interfaz |
| Abrir otro usuario desde el ranking | Pasó en prueba instrumentada de interfaz |
| Seguir | Pasó; se comprobaron ambas relaciones y contadores en Firebase |
| Dejar de seguir | Pasó; se comprobó la restauración de relaciones y contadores |
| Crear, editar, likes y borrar reseña | Pasó desde interfaz; se comprobaron documentos, relaciones de likes y contadores en Firebase. Se verificó cancelar y confirmar la eliminación |
| Dar y retirar like desde Detalle del juego | Pasó; relación de like y contador comprobados en Firebase |
| Abrir perfil del autor desde una reseña | Pasó desde interfaz |
| Controles de edición y borrado en reseña ajena | Se comprobó que no se muestran en la interfaz; no es una auditoría de reglas de seguridad de Firebase |
| Editar y restaurar perfil | Pasó desde interfaz; biografía y username originales restaurados y comprobados en Firebase |
| Inicio de sesión | Se comprobaron validaciones de campos vacíos y contraseña corta, sin cambiar la sesión actual. No se probó reautenticación con contraseña |
| Cerrar sesión y registro | No ejecutados: se conservó la sesión de la cuenta de prueba existente y no se crearon cuentas |
| Subida de imagen | No ejecutada: la ruta de almacenamiento sustituye la foto actual |

La creación, apertura y edición de reseña completa se repitieron después de la simplificación, utilizando una reseña de prueba. Los campos de autor y juego se conservan después de editar.

## Ejecución con la cuenta de prueba existente

El usuario inició sesión con una cuenta de prueba y eligió utilizarla en lugar de crear cuentas temporales. Se eliminó del código de pruebas la creación y eliminación de cuentas de Firebase.

Para conservar la sesión local, las pruebas se instalaron con `adb install -r` y se ejecutaron directamente con `am instrument`, sin volver a usar el ejecutor de Gradle que restableció los datos locales anteriormente.

La batería `AppRegressionTest` exige `liveChecks=true` y una sesión ya iniciada. Las reseñas de prueba usan un marcador `QA_REGRESION_…`; la limpieza elimina únicamente esos documentos y sus likes. Las pruebas de seguimiento restablecen la relación y los contadores, y la edición de perfil restaura los valores originales. La cuenta existente no se elimina ni se cierra.

Comandos de reproducción, después de iniciar sesión con una cuenta de prueba en el emulador:

```sh
./gradlew :app:assembleDebug :app:assembleDebugAndroidTest :app:testDebugUnitTest
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb shell am instrument -w -e liveChecks true -e class com.example.voxel_review.AppRegressionTest com.example.voxel_review.test/androidx.test.runner.AndroidJUnitRunner
```

## Funciones previas incompletas encontradas por lectura del código

- Modo oscuro: `SettingsRoute` pasa `darkMode = false` y la acción del interruptor está vacía.
- Color de tema y notificaciones: se cambian en el estado del ViewModel, sin persistencia; el color no se conecta al tema global y el interruptor no configura FCM.
- “Ver todos” de Notificaciones: callbacks vacíos. Los datos de esta pantalla son proveedores locales.
- Géneros de Descubrir y categorías de Novedades: cambian la selección, pero no filtran los juegos.
- Rankings “Juegos”: cambia el título, pero la lista sigue utilizando usuarios.

Estas funciones no pueden declararse operativas solo porque la pantalla se abra. No se implementaron como parte de esta revisión de regresión.
