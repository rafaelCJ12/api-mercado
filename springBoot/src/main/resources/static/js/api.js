const API_BASE = '/api';

async function request(path, opts = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...opts
  });
  if (!res.ok) throw new Error((await res.json()).message || res.statusText);
  return res.status === 204 ? null : res.json();
}

// ignore