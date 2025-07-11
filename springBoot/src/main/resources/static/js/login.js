// js/login.js

const API_URL = ''; // vazio se front e back no mesmo host:porta
const form = document.getElementById('loginForm');
const errorMsg = document.getElementById('errorMsg');

form.addEventListener('submit', async e => {
  e.preventDefault();
  errorMsg.classList.add('hidden');

  const cpf = document.getElementById('cpf').value.trim();
  const senha = document.getElementById('senha').value;

  try {
    const res = await fetch(`${API_URL}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cpf, senha })
    });

    if (!res.ok) {
      // Extrai mensagem de erro JSON (error) ou exibe genérica
      const payload = await res.json().catch(() => ({}));
      throw new Error(payload.error || 'Falha na autenticação');
    }

    const { token } = await res.json();
    localStorage.setItem('token', token);
    // Redireciona para página inicial
    window.location.href = 'index.html';
  } catch (err) {
    errorMsg.textContent = err.message;
    errorMsg.classList.remove('hidden');
  }
});
