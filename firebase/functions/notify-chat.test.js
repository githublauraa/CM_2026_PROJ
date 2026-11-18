const assert = require("node:assert/strict");
const test = require("node:test");
const { notifyChatMessage } = require("./notify-chat");

function fixture(participantIds = ["autor", "destinatario"]) {
  const now = 2_000_000;
  const event = {
    params: { chatId: "autor~destinatario", messageId: "mensaje-1" },
    data: {
      createTime: { toMillis: () => now },
      get: (field) => ({ senderId: "autor", text: "mensaje privado" })[field],
      ref: { parent: { parent: { get: async () => ({ get: () => participantIds }) } } },
    },
  };

  const requestedUsers = [];
  const saved = new Map();
  const db = {
    collection: (collection) => ({
      doc: (userId) => {
        if (collection === "users") {
          return { get: async () => ({ get: (field) => field === "username" ? "Crítico" : "" }) };
        }
        if (collection === "chatNotifications") {
          return { collection: () => ({
            doc: (id) => ({ set: async (notice) => saved.set(`${userId}/${id}`, notice) }),
          }) };
        }
        requestedUsers.push(userId);
        return {
          collection: () => ({
            get: async () => ({ docs: ["equipo-1", "equipo-2", "equipo-1"].map((token) => ({ get: () => token })) }),
          }),
        };
      },
    }),
  };

  const pushes = [];
  const messaging = {
    sendEachForMulticast: async (push) => {
      pushes.push(push);
      return { responses: push.tokens.map(() => ({ success: true })) };
    },
  };

  return { now, event, db, messaging, requestedUsers, pushes, saved };
}

test("notifica solo a los dispositivos del destinatario y deduplica tokens", async () => {
  const { event, db, messaging, now, requestedUsers, pushes } = fixture();
  await notifyChatMessage(event, db, messaging, now);

  assert.deepEqual(requestedUsers, ["destinatario"]);
  assert.equal(pushes.length, 1);
  assert.deepEqual(pushes[0].tokens, ["equipo-1", "equipo-2"]);
  assert.deepEqual(pushes[0].data, {
    type: "chat_message",
    recipientId: "destinatario",
    senderId: "autor",
    chatId: "autor~destinatario",
    messageId: "mensaje-1",
  });
});

test("omite el push vencido y guarda el aviso en el historial", async () => {
  const outside = fixture(["otro", "destinatario"]);
  await notifyChatMessage(outside.event, outside.db, outside.messaging, outside.now);
  assert.deepEqual(outside.requestedUsers, []);
  assert.equal(outside.saved.size, 0);

  const expired = fixture();
  await notifyChatMessage(expired.event, expired.db, expired.messaging, expired.now + 20 * 60 * 1000);
  assert.deepEqual(expired.requestedUsers, []);
  const notice = expired.saved.get("destinatario/autor~destinatario_mensaje-1");
  assert.equal(notice.senderId, "autor");
  assert.equal(notice.senderName, "Crítico");
  assert.equal(notice.text, undefined);
});

test("la reentrega de una foto conserva un solo aviso", async () => {
  const { event, db, messaging, now, saved } = fixture();
  const getMessageField = event.data.get;
  event.data.get = (field) => field === "photoPath" ? "chatPhotos/foto.jpg" : getMessageField(field);

  await notifyChatMessage(event, db, messaging, now);
  await notifyChatMessage(event, db, messaging, now);

  assert.equal(saved.size, 1);
  assert.equal(saved.values().next().value.isPhoto, true);
});
