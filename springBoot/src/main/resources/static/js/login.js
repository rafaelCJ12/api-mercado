// js/login.js
const API_URL = ''; // deixe em branco se front/back na mesma origem
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
      if (res.status === 401) {
        throw new Error('CPF ou senha incorretos.');
      }
      const errPayload = await res.json().catch(() => ({}));
      throw new Error(errPayload.error || 'Erro ao conectar ao servidor.');
    }

    const { token } = await res.json();
    localStorage.setItem('token', token);
    // Redireciona para a página inicial do sistema
    window.location.replace('index.html');
  } catch (err) {
    errorMsg.textContent = err.message;
    errorMsg.classList.remove('hidden');
  }
});
