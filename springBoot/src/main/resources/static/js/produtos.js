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

// js/produtos.js

const API = 'http://localhost:8080/api';

// Função para buscar e renderizar a lista de produtos
async function fetchProdutos() {
  try {
    const res = await fetch(`${API}/produtos`);
    if (!res.ok) throw new Error('Erro ao listar produtos');
    const list = await res.json();
    const tbody = document.getElementById('produtos-tbody');
    tbody.innerHTML = '';
    list.forEach(p => {
      const id    = p.codigoProduto ?? p.codigo;
      const nome  = p.nome;
      const valor = p.valorUnitario ?? p.valor;
      const qtd   = p.quantidade;
      const massa = p.ehUnidadeMassa;
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${id}</td>
        <td>${nome}</td>
        <td>${valor.toFixed(2)}</td>
        <td>${qtd}</td>
        <td>${massa}</td>
        <td>
          <button type="button" onclick="editProduto(${id})">Editar</button>
        </td>`;
      tbody.appendChild(tr);
    });
  } catch (err) {
    alert(err.message);
  }
}

// Reseta o formulário
function resetForm() {
  document.getElementById('produto-form').reset();
  document.getElementById('produto-id').value = '';
}

// Preenche o formulário para edição
async function editProduto(id) {
  try {
    const res = await fetch(`${API}/produto/${id}`);
    if (!res.ok) throw new Error('Produto não encontrado');
    const p = await res.json();
    document.getElementById('produto-id').value            = p.codigoProduto ?? p.codigo;
    document.getElementById('produto-nome').value          = p.nome;
    document.getElementById('produto-valorunitario').value = p.valorUnitario ?? p.valor;
    document.getElementById('produto-quantidade').value    = p.quantidade;
    document.getElementById('produto-ehunidademassa').checked = p.ehUnidadeMassa;
  } catch (err) {
    alert(err.message);
  }
}

// Handler de submit para criar ou atualizar
document.getElementById('produto-form').addEventListener('submit', async e => {
  e.preventDefault();
  const id    = document.getElementById('produto-id').value;
  const nome  = document.getElementById('produto-nome').value;
  const valor = parseFloat(document.getElementById('produto-valorunitario').value);
  const qtd   = parseFloat(document.getElementById('produto-quantidade').value);
  const massa = document.getElementById('produto-ehunidademassa').checked;

  const body = { nome, valorunitario: valor, quantidade: qtd, ehunidademassa: massa };
  const url    = id
    ? `${API}/atualizar-produto/${id}`
    : `${API}/adicionar-novo-produto`;
  const method = id ? 'PUT' : 'POST';

  try {
    const res = await fetch(url, {
      method,
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body)
    });
    if (!res.ok) throw new Error('Falha ao salvar produto');
    resetForm();
    fetchProdutos();
  } catch (err) {
    alert(err.message);
  }
});

// Handler do botão cancelar
document.getElementById('produto-form-cancel').addEventListener('click', e => {
  e.preventDefault();
  resetForm();
});

window.addEventListener('load', fetchProdutos);
