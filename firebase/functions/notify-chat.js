const MAX_NOTIFICATION_AGE_MS = 15 * 60 * 1000;
const INVALID_TOKEN_CODES = new Set([
  "messaging/registration-token-not-registered",
  "messaging/invalid-registration-token",
]);

async function notifyChatMessage(event, db, messaging, now = Date.now()) {
  const message = event.data;
  if (!message) return;

  const senderId = message.get("senderId");
  const conversation = await message.ref.parent.parent.get();
  const participantIds = conversation.get("participantIds");
  if (!Array.isArray(participantIds) || participantIds.length !== 2 || !participantIds.includes(senderId)) return;

  const recipientId = participantIds.find((uid) => uid !== senderId);
  if (!recipientId) return;

  const { chatId, messageId } = event.params;
  const sender = await db.collection("users").doc(senderId).get();
  const senderName = sender.get("username");
  const senderPhotoUrl = sender.get("photoUrl");

  // Una ruta fija evita duplicados cuando Firestore vuelve a entregar el evento.
  await db.collection("chatNotifications").doc(recipientId).collection("messages")
    .doc(`${chatId}_${messageId}`).set({
      senderId,
      senderName: typeof senderName === "string" ? senderName : "",
      senderPhotoUrl: typeof senderPhotoUrl === "string" ? senderPhotoUrl : "",
      isPhoto: Boolean(message.get("photoPath")),
      createdAt: message.createTime,
    });

  // Mantiene el historial aunque el aviso push haya vencido o no haya dispositivos.
  if (now - message.createTime.toMillis() >= MAX_NOTIFICATION_AGE_MS) return;

  const devices = await db.collection("chatPushRegistrations").doc(recipientId).collection("devices").get();
  const devicesByToken = new Map();
  for (const device of devices.docs) {
    const token = device.get("token");
    if (typeof token !== "string" || !token) continue;
    const registrations = devicesByToken.get(token) || [];
    registrations.push(device.ref);
    devicesByToken.set(token, registrations);
  }

  const tokens = [...devicesByToken.keys()];
  let failed = false;
  for (let start = 0; start < tokens.length; start += 500) {
    const batch = tokens.slice(start, start + 500);
    const result = await messaging.sendEachForMulticast({
      tokens: batch,
      data: {
        type: "chat_message",
        recipientId,
        senderId,
        chatId: event.params.chatId,
        messageId: event.params.messageId,
      },
      android: { priority: "high", ttl: MAX_NOTIFICATION_AGE_MS },
    });

    for (const [index, response] of result.responses.entries()) {
      if (response.success) continue;
      if (INVALID_TOKEN_CODES.has(response.error?.code)) {
        const token = batch[index];
        for (const ref of devicesByToken.get(token)) {
          await db.runTransaction(async (transaction) => {
            const current = await transaction.get(ref);
            if (current.get("token") === token) transaction.delete(ref);
          });
        }
      } else {
        failed = true;
      }
    }
  }

  if (failed) throw new Error("No se pudo notificar a todos los dispositivos del destinatario.");
}

module.exports = { notifyChatMessage };
