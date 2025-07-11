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

// js/compras.js

const API = 'http://localhost:8080/api';

// Buscar e renderizar todas as compras
async function fetchCompras() {
  try {
    const res = await fetch(`${API}/compras`);
    if (!res.ok) throw new Error('Erro ao listar compras');
    const list = await res.json();
    const tbody = document.getElementById('compras-tbody');
    tbody.innerHTML = '';
    list.forEach(c => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${c.codigo}</td>
        <td>${c.responsavel}</td>
        <td>${c.tipoPagamento}</td>
        <td>${c.valorRecebido.toFixed(2)}</td>
        <td>${c.status}</td>
        <td>${new Date(c.dataHora).toLocaleString('pt-BR')}</td>`;
      tbody.appendChild(tr);
    });
  } catch (err) {
    alert(err.message);
  }
}

// Registrar nova compra
document.getElementById('compra-create-form').addEventListener('submit', async e => {
  e.preventDefault();
  const responsavel   = parseInt(document.getElementById('comp-responsavel').value, 10);
  const tipoPagamento = parseInt(document.getElementById('comp-tipoPagamento').value, 10);
  const valorRecebido = parseFloat(document.getElementById('comp-valorRecebido').value);
  const status        = parseInt(document.getElementById('comp-status').value, 10);

  try {
    const res = await fetch(`${API}/criar-nova-compra`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ responsavel, tipoPagmento: tipoPagamento, valorRecebido, status })
    });
    if (!res.ok) throw new Error('Erro ao registrar compra');
    alert('Compra registrada com sucesso!');
    e.target.reset();
    fetchCompras();
  } catch (err) {
    alert(err.message);
  }
});

// Atualizar compra existente
document.getElementById('compra-update-form').addEventListener('submit', async e => {
  e.preventDefault();
  const id             = parseInt(document.getElementById('comp-id-update').value, 10);
  const tipoPagamento  = parseInt(document.getElementById('comp-tipoPagamento-update').value || '0', 10);
  const valorRecebido  = parseFloat(document.getElementById('comp-valorRecebido-update').value || '0');
  const status         = parseInt(document.getElementById('comp-status-update').value || '0', 10);

  try {
    const res = await fetch(`${API}/atualiza-compra/${id}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ responsavel: 0, tipoPagmento: tipoPagamento, valorRecebido, status })
    });
    if (!res.ok) throw new Error('Erro ao atualizar compra');
    alert('Compra atualizada com sucesso!');
    e.target.reset();
    fetchCompras();
  } catch (err) {
    alert(err.message);
  }
});

// Buscar compra por ID
document.getElementById('compra-get-form').addEventListener('submit', async e => {
  e.preventDefault();
  const id = parseInt(document.getElementById('comp-id-get').value, 10);
  try {
    const res = await fetch(`${API}/compra/${id}`);
    if (!res.ok) throw new Error('Compra não encontrada');
    const obj = await res.json();
    document.getElementById('compra-detail').textContent = JSON.stringify(obj, null, 2);
  } catch (err) {
    alert(err.message);
  }
});

// Inicializa a lista de compras ao carregar
window.addEventListener('load', fetchCompras);
