// Autenticação: checa token e injeta header em todas as requisições
const token = localStorage.getItem('token');
if (!token) {
  window.location.href = 'login.html';
}
const _origFetch = window.fetch;
window.fetch = (url, opts = {}) => {
  opts.headers = {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`,
    ...opts.headers
  };
  return _origFetch(url, opts);
};

// js/funcionarios.js

const API = 'http://localhost:8080/api';

// Criar novo funcionário
document.getElementById('func-create-form').addEventListener('submit', async e => {
  e.preventDefault();
  const nome  = document.getElementById('func-nome').value;
  const cpf   = document.getElementById('func-cpf').value;
  const senha = document.getElementById('func-senha').value;
  const tipo  = parseInt(document.getElementById('func-tipo').value, 10);

  try {
    const res = await fetch(`${API}/adicionar-novo-funcionario`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nome, cpf, senha, tipo })
    });
    if (!res.ok) throw new Error('Erro ao criar funcionário');
    alert('Funcionário criado com sucesso!');
    e.target.reset();
  } catch (err) {
    alert(err.message);
  }
});

// Login de funcionário
document.getElementById('func-login-form').addEventListener('submit', async e => {
  e.preventDefault();
  const cpf   = document.getElementById('login-cpf').value;
  const senha = document.getElementById('login-senha').value;

  try {
    const res = await fetch(`${API}/login`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nome: '', cpf, senha, tipo: 0 })
    });
    const text = await res.text();
    document.getElementById('login-result').innerText = text;
  } catch (err) {
    alert('Erro no login: ' + err.message);
  }
});

// Atualizar dados do funcionário
document.getElementById('func-update-form').addEventListener('submit', async e => {
  e.preventDefault();
  const id    = parseInt(document.getElementById('func-id-update').value, 10);
  const nome  = document.getElementById('func-nome-update').value;
  const cpf   = document.getElementById('func-cpf-update').value;
  const senha = document.getElementById('func-senha-update').value;
  const tipo  = parseInt(document.getElementById('func-tipo-update').value || '0', 10);

  const payload = { nome, cpf, senha, tipo };

  try {
    const res = await fetch(`${API}/atualizar-dados/${id}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error('Erro ao atualizar dados');
    alert('Dados atualizados com sucesso!');
    e.target.reset();
  } catch (err) {
    alert(err.message);
  }
});
