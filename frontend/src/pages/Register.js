import '../styles/register.css'

function Register() {

    const handleSubmit = async (e) => {

    }

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input 
                    type="text"
                    name='userName'
                    placeholder="Usuario"
                />
                <input 
                    type="password"
                    name='userPassword'
                    placeholder="Senha"
                />
                <input 
                    type="password"
                    name='userPasswordAgain'
                    placeholder="Senha Novamente"
                />
                <button 
                    type="submit">
                    Cadastrar Usuario
                </button>
            </form>
        </div>
    );
}

export default Register;
