# Administración del catálogo

La app Android muestra **Administrar catálogo** dentro de Configuración solo si el token de Firebase Authentication contiene `admin: true`. No se usa el perfil en `users` como fuente de permisos ni se permite elegir un rol al registrarse. La ruta administrativa también verifica el token al cargar y antes de modificar datos.

## Activación en Firebase

1. En las reglas actuales de Firestore, reemplaza el bloque de `video-Games` por [catalog.rules](catalog.rules), dentro de `match /databases/{database}/documents`. Conserva las reglas de las demás colecciones. Excluye `video-Games` de cualquier regla general que conceda escritura: las reglas coincidentes se combinan con OR y una regla abierta anularía esta protección. El archivo `firestore.rules` contiene las reglas completas publicadas el 19 de noviembre de 2026: conserva el acceso anterior de las otras colecciones y excluye `video-Games` de ese permiso general. Antes de futuros despliegues, revisa que producción no haya recibido cambios externos.
2. La persona administradora debe tener una cuenta ya registrada en Voxel Review.
3. En una máquina de confianza, configura Application Default Credentials con permisos para gestionar usuarios del proyecto (o `GOOGLE_APPLICATION_CREDENTIALS` apuntando a un archivo privado fuera del repositorio). Instala las dependencias con `npm ci --prefix firebase/functions`.
4. Desde la raíz ejecuta, sustituyendo el correo:

   ```sh
   node firebase/functions/scripts/set-admin.cjs voxelreview-78caf correo-del-admin@example.com grant
   ```

   También admite UID. Conserva otros claims. Para retirar el rol, cambia `grant` por `revoke`. No genera cuentas ni contraseñas. No incluyas claves de servicio en el APK o en Git. Un token ya emitido puede conservar el permiso hasta que expire (aproximadamente una hora).
5. Vuelve a entrar en Configuración. La app fuerza la renovación del token. Abre **Administrar catálogo**, agrega el juego y consulta Descubrir.

El rol se asigna mediante el Admin SDK en un entorno privilegiado, siguiendo el mecanismo de [custom claims de Firebase](https://firebase.google.com/docs/auth/admin/custom-claims).

## Datos y compatibilidad

Se escriben los campos existentes `videoGameId`, `name`, `description`, `developer`, `platform`, `releaseDate`, `imageUrl`, y los nuevos `genres` (lista) y `archived` (booleano). Las plataformas se guardan separadas por comas en el campo existente. La portada usa una URL HTTPS; esta versión no sube archivos. El formulario valida fechas reales y límites de tamaño. Al editar juegos anteriores se deben completar los campos obligatorios.

Los juegos antiguos sin `archived` se siguen mostrando. Archivar oculta el juego de las listas del catálogo; el documento, sus reseñas y las entradas de bibliotecas se conservan. El panel permite restaurarlo. No se permiten eliminaciones desde clientes, tampoco para administradores.

La comprobación de nombres duplicados ignora mayúsculas y espacios al inicio/final e incluye archivados. Es una validación de la app, no una restricción única transaccional: dos administradores que guarden simultáneamente podrían crear duplicados.

## Verificación manual con dos cuentas

- Cuenta normal: no aparece el acceso; un intento directo de crear, modificar, archivar o borrar en `video-Games` debe recibir `permission-denied`.
- Cuenta admin: crear y editar un juego válido; comprobar que conserva el ID y que aparece al volver a Descubrir. Probar campos vacíos, fecha imposible, portada sin HTTPS y nombre repetido.
- Archivar, comprobar que desaparece de Descubrir y que sus reseñas y biblioteca siguen disponibles; restaurar y comprobar que reaparece.
- Cambiar un campo `admin` o `role` del perfil no debe conceder privilegios. Solo importa el claim firmado de Authentication.

## Pruebas automatizadas

- Android: `./gradlew :app:testDebugUnitTest` prueba validaciones del formulario, bloqueo del panel para cuentas normales, guardado, errores y archivado/restauración.
- Reglas: inicia el emulador de Firestore en `127.0.0.1:8089` y ejecuta `node --test firebase/tests/catalog.rules.test.cjs` (Node 22 o posterior, sin dependencias adicionales). El test carga las reglas completas de `firestore.rules` en el proyecto **demo-voxel-admin**, únicamente en localhost, y verifica permisos, validación y compatibilidad con documentos anteriores. No necesita credenciales ni accede a producción.

## Activación realizada

El 19 de noviembre de 2026 se creó la cuenta `admin@admin.com` (UID `o7DlqJ5a0ANSIbUsWcI7C5ocBpm1`), su perfil en `users` y el claim `admin: true`. Se publicaron las reglas de `firestore.rules` en `voxelreview-78caf` después de probarlas en el emulador. La contraseña no se guarda en el repositorio.

Para desplegar futuros cambios revisados únicamente a estas reglas: `firebase deploy --config firebase/firebase.json --only firestore:rules --project voxelreview-78caf`.
