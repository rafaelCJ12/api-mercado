const baseComp = '/compras';

const formC       = document.getElementById('formCompra');
const respC       = document.getElementById('compResponsavel');
const tipoPgtoC   = document.getElementById('compTipoPagamento');
const valorRecC   = document.getElementById('compValorRecebido');
const statusC     = document.getElementById('compStatus');
const tabelaC     = document.getElementById('listaCompras');

async function carregarCompras() {
  const res = await fetch(baseComp);
  const dados = await res.json();
  tabelaC.innerHTML = dados.map(c => `
    <tr>
      <td>${c.responsavel}</td>
      <td>${c.tipoPagmento}</td>
      <td>${c.valorRecebido}</td>
      <td>${c.status}</td>
    </tr>
  `).join('');
}

async function registrarCompra(e) {
  e.preventDefault();
  const dto = {
    responsavel: parseInt(respC.value),
    tipoPagmento: parseInt(tipoPgtoC.value),
    valorRecebido: parseFloat(valorRecC.value),
    status: parseInt(statusC.value)
  };
  await fetch(baseComp, {
    method: 'POST',
    headers: {'Content-Type':'application/json'},
    body: JSON.stringify(dto)
  });
  formC.reset();
  carregarCompras();
}

formC.addEventListener('submit', registrarCompra);
window.addEventListener('DOMContentLoaded', carregarCompras);
