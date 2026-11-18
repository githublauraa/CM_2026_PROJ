const { initializeApp } = require("firebase-admin/app");
const { getFirestore } = require("firebase-admin/firestore");
const { getMessaging } = require("firebase-admin/messaging");
const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const { notifyChatMessage } = require("./notify-chat");

initializeApp();

exports.notifyNewChatMessage = onDocumentCreated(
  { document: "chats/{chatId}/messages/{messageId}", retry: true },
  (event) => notifyChatMessage(event, getFirestore(), getMessaging())
);
