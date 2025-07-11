const apiBase = '/produtos';

const form = document.getElementById('formProduto');
const idField = document.getElementById('produtoId');
const nomeField = document.getElementById('nome');
const precoField = document.getElementById('preco');
const qtdField = document.getElementById('quantidade');
const tbody = document.getElementById('tbodyProdutos');
const btnCancelar = document.getElementById('btnCancelar');

async function listar() {
  const res = await fetch(apiBase);
  const dados = await res.json();
  tbody.innerHTML = dados.map(p => `
    <tr>
      <td>${p.id}</td>
      <td>${p.nome}</td>
      <td>${p.preco.toFixed(2)}</td>
      <td>${p.quantidade}</td>
      <td>
        <button onclick="editar(${p.id})">✏️</button>
        <button onclick="remover(${p.id})">🗑️</button>
      </td>
    </tr>
  `).join('');
}

async function salvar(e) {
  e.preventDefault();
  const payload = {
    nome: nomeField.value,
    preco: parseFloat(precoField.value),
    quantidade: parseInt(qtdField.value)
  };
  let method = 'POST', url = apiBase;
  if (idField.value) {
    method = 'PUT';
    url += `/${idField.value}`;
  }
  await fetch(url, {
    method, headers:{'Content-Type':'application/json'},
    body: JSON.stringify(payload)
  });
  resetForm();
  listar();
}

async function editar(id) {
  const res = await fetch(`${apiBase}/${id}`);
  const p = await res.json();
  idField.value = p.id;
  nomeField.value = p.nome;
  precoField.value = p.preco;
  qtdField.value = p.quantidade;
  btnCancelar.style.display = 'inline-block';
}

async function remover(id) {
  if (!confirm('Confirmar exclusão?')) return;
  await fetch(`${apiBase}/${id}`, { method: 'DELETE' });
  listar();
}

function resetForm() {
  form.reset();
  idField.value = '';
  btnCancelar.style.display = 'none';
}

btnCancelar.addEventListener('click', resetForm);
form.addEventListener('submit', salvar);

// inicialização
listar();
