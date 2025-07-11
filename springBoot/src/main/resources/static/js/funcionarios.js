const baseFunc   = '/api/funcionarios';
const formF      = document.getElementById('formFuncionario');
const codigoF    = document.getElementById('funcCodigo');
const nomeF      = document.getElementById('funcNome');
const cpfF       = document.getElementById('funcCpf');
const senhaF     = document.getElementById('funcSenha');
const tipoF      = document.getElementById('funcTipo');
const tabelaF    = document.getElementById('listaFuncionarios');
const btnCancelF = document.getElementById('cancelarFunc');

async function carregarFuncionarios() {
  const res = await fetch(baseFunc);
  const dados = await res.json();
  tabelaF.innerHTML = dados.map(u => `
    <tr>
      <td>${u.codigo}</td>
      <td>${u.nome}</td>
      <td>${u.cpf}</td>
      <td>${u.tipo}</td>
      <td>
        <button onclick="editarFuncionario(${u.codigo})">✏️</button>
        <button onclick="deletarFuncionario(${u.codigo})">🗑️</button>
      </td>
    </tr>
  `).join('');
}

async function salvarFuncionario(e) {
  e.preventDefault();
  const dto = {
    nome: nomeF.value,
    cpf: cpfF.value,
    senha: senhaF.value,
    tipo: parseInt(tipoF.value)
  };
  let method = 'POST', url = '/api/funcionario';
  if (codigoF.value) {
    method = 'PUT';
    url = `/api/funcionarios/${codigoF.value}`;
  }
  await fetch(url, {
    method,
    headers:{'Content-Type':'application/json'},
    body: JSON.stringify(dto)
  });
  resetFuncionario();
  carregarFuncionarios();
}

async function editarFuncionario(codigo) {
  const res = await fetch(`${baseFunc}/${codigo}`);
  const u = await res.json();
  codigoF.value = u.codigo;
  nomeF.value   = u.nome;
  cpfF.value    = u.cpf;
  senhaF.value  = u.senha;
  tipoF.value   = u.tipo;
  btnCancelF.style.display = 'inline-block';
}

async function deletarFuncionario(codigo) {
  if (!confirm('Confirma?')) return;
  await fetch(`/api/funcionarios/${codigo}`, { method: 'DELETE' });
  carregarFuncionarios();
}

function resetFuncionario() {
  formF.reset();
  codigoF.value = '';
  btnCancelF.style.display = 'none';
}

btnCancelF.addEventListener('click', resetFuncionario);
formF.addEventListener('submit', salvarFuncionario);
window.addEventListener('DOMContentLoaded', carregarFuncionarios);
