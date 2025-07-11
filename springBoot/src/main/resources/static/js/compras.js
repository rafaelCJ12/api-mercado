const compBase = '/compras';

const formC = document.getElementById('formCompra');
const idC   = document.getElementById('compId');
const prodC = document.getElementById('compProdId');
const qtdC  = document.getElementById('compQtd');
const tipoC = document.getElementById('compTipo');
const tbc   = document.getElementById('tbodyCompras');
const btnCCancel = document.getElementById('btnCompCancelar');

async function listarC() {
  const res = await fetch(compBase);
  const data = await res.json();
  tbc.innerHTML = data.map(c => `
    <tr>
      <td>${c.id}</td>
      <td>${c.produtoId}</td>
      <td>${c.quantidade}</td>
      <td>${c.tipoPagamento}</td>
      <td>
        <button onclick="editarC(${c.id})">✏️</button>
        <button onclick="removerC(${c.id})">🗑️</button>
      </td>
    </tr>
  `).join('');
}

async function salvarC(e) {
  e.preventDefault();
  const payload = {
    produtoId: parseInt(prodC.value),
    quantidade: parseInt(qtdC.value),
    tipoPagamento: parseInt(tipoC.value)
  };
  let method = 'POST', url = compBase;
  if (idC.value) {
    method = 'PUT';
    url += `/${idC.value}`;
  }
  await fetch(url, {
    method, headers:{'Content-Type':'application/json'},
    body: JSON.stringify(payload)
  });
  resetC();
  listarC();
}

async function editarC(id) {
  const res = await fetch(`${compBase}/${id}`);
  const c = await res.json();
  idC.value = c.id;
  prodC.value = c.produtoId;
  qtdC.value = c.quantidade;
  tipoC.value = c.tipoPagamento;
  btnCCancel.style.display = 'inline-block';
}

async function removerC(id) {
  if (!confirm('Confirmar exclusão?')) return;
  await fetch(`${compBase}/${id}`, { method: 'DELETE' });
  listarC();
}

function resetC() {
  formC.reset();
  idC.value = '';
  btnCCancel.style.display = 'none';
}

btnCCancel.addEventListener('click', resetC);
formC.addEventListener('submit', salvarC);

// inicialização
listarC();
