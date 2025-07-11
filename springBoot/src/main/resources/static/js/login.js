// js/login.js

// Espera o DOM estar pronto para garantir que o form exista
document.addEventListener('DOMContentLoaded', () => {
  const API_URL  = '';   // se front/back na mesma origem
  const form     = document.getElementById('loginForm');
  const errorMsg = document.getElementById('errorMsg');

  form.addEventListener('submit', async e => {
    // **impede** o submit padrão de navegador
    e.preventDefault();
    errorMsg.classList.add('hidden');

    const cpf   = document.getElementById('cpf').value.trim();
    const senha = document.getElementById('senha').value;

    try {
      const res = await fetch(`${API_URL}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ cpf, senha })
      });

      if (!res.ok) {
        if (res.status === 401) throw new Error('CPF ou senha incorretos.');
        const payload = await res.json().catch(() => ({}));
        throw new Error(payload.error || 'Erro ao conectar ao servidor.');
      }

      const { token } = await res.json();
      localStorage.setItem('token', token);
      // redireciona para home imediatamente
      window.location.replace('index.html');
    } catch (err) {
      errorMsg.textContent = err.message;
      errorMsg.classList.remove('hidden');
    }
  });
});
