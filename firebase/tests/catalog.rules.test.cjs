// Tests only against a local emulator, using its unsigned test tokens. No credentials.
const { before, test } = require('node:test');
const assert = require('node:assert/strict');
const { readFileSync } = require('node:fs');
const { join } = require('node:path');
const host = process.env.FIRESTORE_EMULATOR_HOST || '127.0.0.1:8089';
if (!/^(127\.0\.0\.1|localhost):\d+$/.test(host)) throw new Error('Local emulator required');
const project = 'demo-voxel-admin';
const root = `http://${host}`;
const documents = `${root}/v1/projects/${project}/databases/(default)/documents`;
const encode = value => Buffer.from(JSON.stringify(value)).toString('base64url');
function token(admin) {
  const now = Math.floor(Date.now() / 1000);
  return `${encode({ alg: 'none', typ: 'JWT' })}.${encode({
    iss: `https://securetoken.google.com/${project}`, aud: project, sub: 'test-user',
    user_id: 'test-user', iat: now, exp: now + 3600,
    firebase: { sign_in_provider: 'password', identities: {} }, ...(admin === undefined ? {} : { admin })
  })}.`;
}
function field(value) {
  if (typeof value === 'boolean') return { booleanValue: value };
  if (Array.isArray(value)) return { arrayValue: { values: value.map(field) } };
  return { stringValue: value };
}
async function write(id, data, auth, masks = []) {
  const query = masks.map(key => `updateMask.fieldPaths=${key}`).join('&');
  return fetch(`${documents}/video-Games/${id}${query ? '?' + query : ''}`, {
    method: 'PATCH', headers: { 'Content-Type': 'application/json', Authorization: `Bearer ${auth}` },
    body: JSON.stringify({ fields: Object.fromEntries(Object.entries(data).map(([k, v]) => [k, field(v)])) })
  });
}
function game(id) {
  return { videoGameId: id, name: 'Celeste', description: 'Plataformas', developer: 'Maddy',
    platform: 'PC', releaseDate: '2018-01-25', imageUrl: 'https://example.com/a.jpg', genres: ['Indie'], archived: false };
}
before(async () => {
  const content = readFileSync(join(__dirname, '../firestore.rules'), 'utf8');
  const res = await fetch(`${root}/emulator/v1/projects/${project}:securityRules`, {
    method: 'PUT', headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ rules: { files: [{ name: 'firestore.rules', content }] } })
  });
  const body = await res.json();
  assert.equal(res.status, 200, JSON.stringify(body));
  assert.ok(!(body.issues || []).some(issue => issue.severity === 'ERROR'), JSON.stringify(body));
});
test('admin can create, edit, archive and restore; cannot delete', async () => {
  const auth = token(true), id = 'admin-game';
  assert.equal((await write(id, game(id), auth)).status, 200);
  assert.equal((await write(id, { name: 'Celeste editado' }, auth, ['name'])).status, 200);
  for (const archived of [true, false]) {
    assert.equal((await write(id, { archived }, auth, ['archived'])).status, 200);
  }
  const res = await fetch(`${documents}/video-Games/${id}`, { method: 'DELETE', headers: { Authorization: `Bearer ${auth}` } });
  assert.equal(res.status, 403);
});
test('normal and forged string admin claims cannot write or archive', async () => {
  for (const claim of [undefined, false, 'true']) {
    const auth = token(claim);
    assert.equal((await write('normal-game', game('normal-game'), auth)).status, 403);
    assert.equal((await write('admin-game', { archived: true }, auth, ['archived'])).status, 403);
    assert.equal((await write('admin-game', { name: 'Changed' }, auth, ['name'])).status, 403);
    const deletion = await fetch(`${documents}/video-Games/admin-game`, { method: 'DELETE', headers: { Authorization: `Bearer ${auth}` } });
    assert.equal(deletion.status, 403);
  }
});
test('admin cannot save malformed catalog data or unexpected fields', async () => {
  const variants = [{ name: '' }, { imageUrl: 'http://example.com/a.jpg' }, { genres: [] },
    { genres: ['x'.repeat(61)] }, { description: 'x'.repeat(5001) }, { role: 'admin' }, { releaseDate: '2026-99-99' }];
  for (let i = 0; i < variants.length; i++) {
    const id = `invalid-${i}`;
    assert.equal((await write(id, { ...game(id), ...variants[i] }, token(true))).status, 403);
  }
});
test('legacy game can be archived without rewriting or removing its fields', async () => {
  const id = 'legacy-game';
  assert.equal((await write(id, { name: 'Legacy', description: 'Existing game' }, 'owner')).status, 200);
  assert.equal((await write(id, { archived: true }, token(true), ['archived'])).status, 200);
  const res = await fetch(`${documents}/video-Games/${id}`, { headers: { Authorization: `Bearer ${token()}` } });
  assert.equal(res.status, 200);
  const body = await res.json();
  assert.equal(body.fields.name.stringValue, 'Legacy');
  assert.equal(body.fields.archived.booleanValue, true);
});
test('unauthenticated clients cannot read catalog', async () => {
  assert.equal((await fetch(`${documents}/video-Games/admin-game`)).status, 403);
});
