const listaProd = document.getElementById('lista-prod');
const formProd = document.getElementById('form-prod');
const btnCancelProd = document.getElementById('btn-cancel-prod');

async function carregarProds() {
  const dados = await request('/produtos');
  listaProd.innerHTML = dados.map(p => `
    <tr>
      <td>${p.nome}</td>
      <td>R$ ${p.preco.toFixed(2)}</td>
      <td>${p.estoque}</td>
      <td>
        <button onclick="editarProd(${p.id})">✏️</button>
        <button onclick="excluirProd(${p.id})">🗑️</button>
      </td>
    </tr>
  `).join('');
}
window.editarProd = async id => {
  const p = await request(`/produtos/${id}`);
  document.getElementById('prod-id').value = p.id;
  document.getElementById('prod-nome').value = p.nome;
  document.getElementById('prod-preco').value = p.preco;
  document.getElementById('prod-estoque').value = p.estoque;
};
window.excluirProd = async id => {
  if (confirm('Excluir produto?')) {
    await request(`/produtos/${id}`, { method: 'DELETE' });
    carregarProds();
  }
};

formProd.addEventListener('submit', async e => {
  e.preventDefault();
  const id = document.getElementById('prod-id').value;
  const body = {
    nome: document.getElementById('prod-nome').value,
    preco: parseFloat(document.getElementById('prod-preco').value),
    estoque: parseInt(document.getElementById('prod-estoque').value, 10)
  };
  if (id) {
    await request(`/produtos/${id}`, { method: 'PUT', body: JSON.stringify(body) });
  } else {
    await request('/produtos', { method: 'POST', body: JSON.stringify(body) });
  }
  formProd.reset();
  carregarProds();
});
btnCancelProd.addEventListener('click', _ => formProd.reset());

carregarProds();
