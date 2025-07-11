const formCompra = document.getElementById('form-compra');
const btnNovaCompra = document.getElementById('btn-nova-compra');
const formItem = document.getElementById('form-item');
const secItens = document.getElementById('itens-compra');
const selProduto = document.getElementById('item-produto');
const listaItens = document.getElementById('lista-itens');
const totalCompra = document.getElementById('total-compra');
const compLabel = document.getElementById('compra-atual');

let compraAtual = null;

async function carregarProdutosSelect() {
  const prods = await request('/produtos');
  selProduto.innerHTML = prods.map(p => `<option value="${p.id}">${p.nome}</option>`).join('');
}

async function novaCompra() {
  const c = await request('/compras', { method: 'POST' });
  compraAtual = c;
  mostrarItens();
}

async function carregarCompra(id) {
  compraAtual = await request(`/compras/${id}`);
  mostrarItens();
}

function mostrarItens() {
  if (!compraAtual) return;
  compLabel.textContent = `#${compraAtual.id}`;
  formCompra['comp-id'].value = compraAtual.id;
  formCompra['comp-status'].value = compraAtual.status;
  secItens.classList.remove('hidden');
  listaItens.innerHTML = compraAtual.itens.map(item => `
    <tr>
      <td>${item.produto.nome}</td>
      <td>${item.quantidade}</td>
      <td>R$ ${item.produto.preco.toFixed(2)}</td>
      <td>R$ ${(item.quantidade * item.produto.preco).toFixed(2)}</td>
      <td>
        <button onclick="alterarItem(${item.id})">✏️</button>
        <button onclick="removerItem(${item.id})">🗑️</button>
      </td>
    </tr>
  `).join('');
  const total = compraAtual.itens.reduce((sum, i) => sum + i.quantidade * i.produto.preco, 0);
  totalCompra.textContent = `R$ ${total.toFixed(2)}`;
}

formCompra.addEventListener('submit', async e => {
  e.preventDefault();
  const id = formCompra['comp-id'].value;
  const status = formCompra['comp-status'].value;
  await request(`/compras/${id}/status`, {
    method: 'PUT',
    body: JSON.stringify({ status })
  });
  compraAtual.status = status;
  alert('Status atualizado.');
});

btnNovaCompra.addEventListener('click', _ => novaCompra());

formItem.addEventListener('submit', async e => {
  e.preventDefault();
  const body = {
    produtoId: parseInt(selProduto.value, 10),
    quantidade: parseInt(document.getElementById('item-quant').value, 10)
  };
  await request(`/compras/${compraAtual.id}/itens`, {
    method: 'POST',
    body: JSON.stringify(body)
  });
  compraAtual = await request(`/compras/${compraAtual.id}`);
  mostrarItens();
});

window.removerItem = async itemId => {
  if (!confirm('Remover este item?')) return;
  await request(`/compras/${compraAtual.id}/itens/${itemId}`, { method: 'DELETE' });
  compraAtual = await request(`/compras/${compraAtual.id}`);
  mostrarItens();
};

window.alterarItem = async itemId => {
  const novaQtd = +prompt('Nova quantidade:');
  if (!novaQtd || novaQtd < 1) return;
  await request(`/compras/${compraAtual.id}/itens/${itemId}`, {
    method: 'PUT',
    body: JSON.stringify({ quantidade: novaQtd })
  });
  compraAtual = await request(`/compras/${compraAtual.id}`);
  mostrarItens();
};

// inicia
(async () => {
  await carregarProdutosSelect();
  // se quiser listar últimas compras, poderia chamar uma API aqui
})();
