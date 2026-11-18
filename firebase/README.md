# Firestore para chats

Integra el bloque de [chat.rules](chat.rules) en las reglas de Firestore de Voxel Review, dentro de `match /databases/{database}/documents`. Conserva las reglas de las colecciones existentes. Revisa que ninguna regla general conceda acceso adicional a `chats` o a sus mensajes. Firestore autoriza la operación si cualquiera de las reglas aplicables la permite.

Agrega el índice de [chat.indexes.json](chat.indexes.json) a la configuración de índices existente. La bandeja consulta los chats con `participantIds ARRAY_CONTAINS` y los ordena por `updatedAt DESC`. Firestore agrega el ID del documento como criterio de desempate del índice.

Cada conversación usa `chats/{uidMenor}~{uidMayor}` y guarda sus mensajes en `messages`. El primer envío crea la conversación. Cada envío escribe el mensaje y la vista previa en un lote con hora del servidor. Cada consulta solicita hasta 31 documentos, muestra 30 y usa el documento adicional para determinar si hay una página anterior.

## Fotos en los chats

Publica primero las reglas actualizadas de [Firestore](chat.rules) y agrega el bloque de [Storage](chat.storage.rules) a las reglas del bucket que usa Voxel Review. Conserva las reglas existentes de fotos de perfil y comprueba que ninguna regla general conceda acceso adicional a `chatPhotos`. Habilita el acceso de las reglas de Storage a Firestore cuando Firebase lo solicite al publicarlas.

La app guarda el JPEG en Storage y registra su ruta en el mensaje de Firestore, sin generar una URL pública. Las reglas de Storage comprueban que el solicitante participa en el chat y que el mensaje existe antes de servir la imagen. El autor puede limpiar una subida si el mensaje no llegó a crearse. Los mensajes de texto anteriores siguen siendo válidos.

## Notificaciones de mensajes

`notifyNewChatMessage` se ejecuta al crear un mensaje de texto o una foto. Busca las instalaciones del otro participante y envía el aviso por FCM. Android comprueba que la sesión activa corresponda al destinatario y abre la conversación cuando este toca el aviso.

1. Integra [chat.notifications.rules](chat.notifications.rules) en las reglas vigentes de Firestore. Las instalaciones se guardan en `chatPushRegistrations/{userId}/devices/{installationId}`. Cualquier regla general que también coincida con esa ruta debe excluir `chatPushRegistrations` para que la app no pueda leer tokens.
2. Despliega únicamente la función con `firebase deploy --config firebase/firebase.json --only functions:chat-notifications:notifyNewChatMessage --project voxelreview-78caf` desde la raíz. La cuenta de servicio de la función necesita acceso a Firestore y permiso para enviar por FCM.
3. Instala el APK actualizado en ambos dispositivos. Abre la app con la cuenta destinataria y permite las notificaciones para que esta registre su dispositivo. Envía un mensaje nuevo desde la otra cuenta y toca el aviso.

Para probar la lógica de destinatarios sin enviar avisos reales, ejecuta `npm test --prefix firebase/functions`.

### Historial de avisos en la app

La pantalla Notificaciones consulta los 30 avisos de chat más recientes de la cuenta activa. Los documentos se guardan en `chatNotifications/{userId}/messages/{notificationId}`. Publica el bloque adicional de [chat.notifications.rules](chat.notifications.rules) para habilitar la consulta. Los avisos se generan para mensajes creados después del despliegue.

Si mantienes la función en el `index.js` donde tienes las notificaciones de seguidores y reacciones, agrega este bloque dentro de `notifyNewChatMessage`, justo después de `if (!recipientId) return;` y antes de buscar los dispositivos.

```js
const senderDoc = await db.collection("users").doc(senderId).get();
const sender = senderDoc.data() || {};
const chatMessage = event.data.data();

await db.collection("chatNotifications").doc(recipientId)
    .collection("messages").doc(`${chatId}_${messageId}`).set({
        senderId,
        senderName: typeof sender.username === "string" ? sender.username : "",
        senderPhotoUrl: typeof sender.photoUrl === "string" ? sender.photoUrl : "",
        isPhoto: Boolean(chatMessage.photoPath),
        createdAt: event.data.createTime
    });
```

Desde esa carpeta, despliega solo el cambio con `firebase deploy --only functions:notifyNewChatMessage --project voxelreview-78caf`. Usa el despliegue de tu carpeta o el de esta implementación según dónde esté alojada la función existente.
