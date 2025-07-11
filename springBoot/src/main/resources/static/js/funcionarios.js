const funcBase = '/funcionarios';

const formF = document.getElementById('formFuncionario');
const idF   = document.getElementById('funcId');
const nomeF = document.getElementById('funcNome');
const cpfF  = document.getElementById('funcCpf');
const emailF= document.getElementById('funcEmail');
const tbf   = document.getElementById('tbodyFuncionarios');
const btnFCancel = document.getElementById('btnFuncCancelar');

async function listarF() {
  const res = await fetch(funcBase);
  const data = await res.json();
  tbf.innerHTML = data.map(u => `
    <tr>
      <td>${u.id}</td>
      <td>${u.nome}</td>
      <td>${u.cpf}</td>
      <td>${u.email}</td>
      <td>
        <button onclick="editarF(${u.id})">✏️</button>
        <button onclick="removerF(${u.id})">🗑️</button>
      </td>
    </tr>
  `).join('');
}

async function salvarF(e) {
  e.preventDefault();
  const payload = {
    nome: nomeF.value,
    cpf: cpfF.value,
    email: emailF.value
  };
  let method = 'POST', url = funcBase;
  if (idF.value) {
    method = 'PUT';
    url += `/${idF.value}`;
  }
  await fetch(url, {
    method, headers:{'Content-Type':'application/json'},
    body: JSON.stringify(payload)
  });
  resetF();
  listarF();
}

async function editarF(id) {
  const res = await fetch(`${funcBase}/${id}`);
  const u = await res.json();
  idF.value = u.id;
  nomeF.value = u.nome;
  cpfF.value = u.cpf;
  emailF.value = u.email;
  btnFCancel.style.display = 'inline-block';
}

async function removerF(id) {
  if (!confirm('Confirmar exclusão?')) return;
  await fetch(`${funcBase}/${id}`, { method: 'DELETE' });
  listarF();
}

function resetF() {
  formF.reset();
  idF.value = '';
  btnFCancel.style.display = 'none';
}

btnFCancel.addEventListener('click', resetF);
formF.addEventListener('submit', salvarF);

// inicialização
listarF();
