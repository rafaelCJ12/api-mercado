package com.example.springBoot.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.springBoot.models.FuncionarioModel;

@Repository
public class FuncionarioRepository {
    private JdbcTemplate jdbcTemplate;

    public FuncionarioRepository(JdbcTemplate j) {
        this.jdbcTemplate = j;
    }

    public List<FuncionarioModel> listarFuncionarios() throws DataAccessException{
        String consulta = "select *from funcionarios";

        return this.jdbcTemplate.query(consulta, new FuncionarioRowMapper());
    }

    public FuncionarioModel buscarFuncionarioPorID(long id) throws DataAccessException{
        String consulta = "select *from funcionarios where funcionarioid = ?";
        
        return this.jdbcTemplate.queryForObject(consulta, new FuncionarioRowMapper(), id);
    }

    @Transactional
    public void salvarFuncionario(FuncionarioModel f) throws DataAccessException{
        String consulta = "insert into funcionarios(name, cpf, senha, fktipofunc) values(?, ?, ?, ?)";
        this.jdbcTemplate.update(consulta, f.getNome(), f.getCpf(), f.getSenha(), f.getTipo());

    }

    public long retornaIdFuncionario(FuncionarioModel f) throws DataAccessException{
        String consulta = "select *from funcionarios where cpf = ? and senha  = ?";

        f = this.jdbcTemplate.queryForObject(consulta, new FuncionarioRowMapper(), f.getCpf(), f.getSenha());

        if(f == null) {
            return -1;
        }

        return f.getCodigo();
    }

    @Transactional
    public boolean atualizarNomeFuncionario(FuncionarioModel f) throws DataAccessException{
        String consulta = "update funcionarios set name = ? where funcionarioid = ?";

        return this.jdbcTemplate.update(consulta, f.getNome(),  f.getCodigo()) > 0;
    }

    @Transactional
    public boolean atualizarCpfFuncionario(FuncionarioModel f) throws DataAccessException{
        String consulta = "update funcionarios set cpf = ? where funcionarioid = ?";

        return this.jdbcTemplate.update(consulta, f.getCpf(),  f.getCodigo()) > 0;
    }

    @Transactional
    public boolean atualizarSenhaFuncionario(FuncionarioModel f) throws DataAccessException{
        String consulta = "update funcionarios set cpf = ? where funcionarioid = ?";

        return this.jdbcTemplate.update(consulta, f.getSenha(),  f.getCodigo()) > 0;
    }

    public boolean deletar(long id) throws DataAccessException{
        String consulta = "delete from funcionarios where funcionarioid = ?";

        return this.jdbcTemplate.update(consulta, id) > 0;
    }

    private static class FuncionarioRowMapper implements RowMapper<FuncionarioModel> {
        public FuncionarioModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            FuncionarioModel fm = new FuncionarioModel();

    
            fm.setCodigo(rs.getLong("funcionarioid"));
            fm.setNome(rs.getString("name"));
            fm.setCpf(rs.getString("cpf"));
            fm.setSenha(rs.getString("senha"));
            fm.setTipo(rs.getLong("fktipofunc"));

            return fm;


        }
    }
    
}
