// URL do seu endpoint de autenticação (ajuste se necessário)
const API_LOGIN = 'http://localhost:8080/api/auth/login';

document.getElementById('login-form')
  .addEventListener('submit', async (e) => {
    e.preventDefault();
    const user = document.getElementById('username').value.trim();
    const pass = document.getElementById('password').value.trim();
    const errEl = document.getElementById('error-msg');
    errEl.classList.add('hidden');

    try {
      const resp = await fetch(API_LOGIN, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: user, password: pass })
      });
      if (!resp.ok) {
        const err = await resp.json();
        throw new Error(err.message || 'Falha na autenticação');
      }
      
      const { token } = await resp.json();
      // Salva o token (ex.: JWT) no localStorage e redireciona
      localStorage.setItem('authToken', token);
      window.location.href = 'index.html';
    } catch (error) {
      errEl.textContent = error.message;
      errEl.classList.remove('hidden');
    }
});
