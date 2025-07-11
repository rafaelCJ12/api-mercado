// js/login.js

// -- 0) Se já tiver token, pula direto para a home
const existingToken = localStorage.getItem('token');
if (existingToken) {
  window.location.replace('index.html');
}

// -- 1) Pega referências ao form e ao elemento de mensagem de erro
const form     = document.getElementById('loginForm');
const errorMsg = document.getElementById('errorMsg');

// 2) Escuta o submit (o script já está no fim do body, o form já existe)
form.addEventListener('submit', async e => {
  // impede o comportamento padrão (GET/query params)
  e.preventDefault();
  errorMsg.classList.add('hidden');

  // 3) Colhe CPF e senha
  const cpf   = document.getElementById('cpf').value.trim();
  const senha = document.getElementById('senha').value;

  try {
    // 4) Faz o POST /login com JSON no corpo
    const res = await fetch(`/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cpf, senha })
    });

    if (!res.ok) {
      if (res.status === 401) {
        throw new Error('CPF ou senha incorretos.');
      }
      const payload = await res.json().catch(() => ({}));
      throw new Error(payload.error || 'Falha ao conectar ao servidor.');
    }

    // 5) Se veio certo, pega o token e salva
    const { token } = await res.json();
    localStorage.setItem('token', token);

    // 6) Redireciona para a home
    window.location.replace('index.html');

  } catch (err) {
    // mostra a mensagem de erro
    errorMsg.textContent = err.message;
    errorMsg.classList.remove('hidden');
  }
});
