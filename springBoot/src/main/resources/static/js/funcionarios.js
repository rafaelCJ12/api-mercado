const listaFun = document.getElementById('lista-func');
const formFun = document.getElementById('form-func');
const btnCancelFun = document.getElementById('btn-cancel');

async function carregarFuncs() {
  const dados = await request('/funcionarios');
  listaFun.innerHTML = dados.map(f => `
    <tr>
      <td>${f.nome}</td>
      <td>${f.email}</td>
      <td>${f.role}</td>
      <td>
        <button onclick="editarFun(${f.id})">✏️</button>
        <button onclick="excluirFun(${f.id})">🗑️</button>
      </td>
    </tr>
  `).join('');
}
window.editarFun = async id => {
  const f = await request(`/funcionarios/${id}`);
  document.getElementById('func-id').value = f.id;
  document.getElementById('func-nome').value = f.nome;
  document.getElementById('func-email').value = f.email;
  document.getElementById('func-role').value = f.role;
};
window.excluirFun = async id => {
  if (confirm('Excluir funcionário?')) {
    await request(`/funcionarios/${id}`, { method: 'DELETE' });
    carregarFuncs();
  }
};

formFun.addEventListener('submit', async e => {
  e.preventDefault();
  const id = document.getElementById('func-id').value;
  const body = {
    nome: document.getElementById('func-nome').value,
    email: document.getElementById('func-email').value,
    role: document.getElementById('func-role').value
  };
  if (id) {
    await request(`/funcionarios/${id}`, { method: 'PUT', body: JSON.stringify(body) });
  } else {
    await request('/funcionarios', { method: 'POST', body: JSON.stringify(body) });
  }
  formFun.reset();
  carregarFuncs();
});
btnCancelFun.addEventListener('click', _ => formFun.reset());

carregarFuncs();
