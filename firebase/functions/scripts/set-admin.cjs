#!/usr/bin/env node
// Run only from a trusted machine with Application Default Credentials.
const { initializeApp, applicationDefault } = require('firebase-admin/app');
const { getAuth } = require('firebase-admin/auth');

async function main() {
  const [projectId, identity, mode = 'grant'] = process.argv.slice(2);
  if (!projectId || !identity || !['grant', 'revoke'].includes(mode)) {
    throw new Error('Uso: node scripts/set-admin.cjs PROJECT_ID EMAIL_O_UID [grant|revoke]');
  }
  initializeApp({ credential: applicationDefault(), projectId });
  const auth = getAuth();
  const user = identity.includes('@') ? await auth.getUserByEmail(identity) : await auth.getUser(identity);
  if (user.disabled) throw new Error('La cuenta está deshabilitada.');
  const claims = { ...user.customClaims };
  if (mode === 'grant') claims.admin = true;
  else delete claims.admin;
  await auth.setCustomUserClaims(user.uid, claims);
  console.log(`Rol admin ${mode === 'grant' ? 'asignado' : 'retirado'} para ${user.uid} en ${projectId}.`);
  console.log('Vuelve a abrir Configuración para renovar el token. Los tokens anteriores pueden durar hasta una hora.');
}
main().catch(error => { console.error(error.message); process.exitCode = 1; });
