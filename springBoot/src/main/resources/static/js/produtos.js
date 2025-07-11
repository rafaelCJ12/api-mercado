const baseProd = '/api/produtos';

const formP      = document.getElementById('formProduto');
const codigoP    = document.getElementById('produtoCodigo');
const nomeP      = document.getElementById('produtoNome');
const valorP     = document.getElementById('produtoValor');
const qtdP       = document.getElementById('produtoQuantidade');
const massaP     = document.getElementById('produtoEhUnidadeMassa');
const tabelaP    = document.getElementById('listaProdutos');
const btnCancelP = document.getElementById('cancelarProduto');

async function carregarProdutos() {
  const res = await fetch(baseProd);
  const dados = await res.json();
  tabelaP.innerHTML = dados.map(p => `
    <tr>
      <td>${p.codigo}</td>
      <td>${p.nome}</td>
      <td>${p.valorunitario.toFixed(2)}</td>
      <td>${p.quantidade}</td>
      <td>${p.ehunidademassa}</td>
      <td>
        <button onclick="editarProduto(${p.codigo})">✏️</button>
        <button onclick="deletarProduto(${p.codigo})">🗑️</button>
      </td>
    </tr>
  `).join('');
}

async function salvarProduto(e) {
  e.preventDefault();
  const dto = {
    nome: nomeP.value,
    valorunitario: parseFloat(valorP.value),
    quantidade: parseFloat(qtdP.value),
    ehunidademassa: massaP.value === 'true'
  };
  let method = 'POST', url = baseProd;
  if (codigoP.value) {
    method = 'PUT';
    url += `/${codigoP.value}`;
  }
  await fetch(url, {
    method,
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify(dto)
  });
  resetProduto();
  carregarProdutos();
}

async function editarProduto(codigo) {
  const res = await fetch(`${baseProd}/${codigo}`);
  const p = await res.json();
  codigoP.value = p.codigo;
  nomeP.value   = p.nome;
  valorP.value  = p.valorunitario;
  qtdP.value    = p.quantidade;
  massaP.value  = p.ehunidademassa;
  btnCancelP.style.display = 'inline-block';
}

async function deletarProduto(codigo) {
  if (!confirm('Confirma?')) return;
  await fetch(`${baseProd}/${codigo}`, { method: 'DELETE' });
  carregarProdutos();
}

function resetProduto() {
  formP.reset();
  codigoP.value = '';
  btnCancelP.style.display = 'none';
}

btnCancelP.addEventListener('click', resetProduto);
formP.addEventListener('submit', salvarProduto);
window.addEventListener('DOMContentLoaded', carregarProdutos);
