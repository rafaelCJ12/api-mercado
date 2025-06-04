package com.example.springBoot.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.springBoot.models.ProdutoModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProdutoRepository {

    private JdbcTemplate jdbcTemplate;

    public ProdutoRepository(JdbcTemplate j) {
        this.jdbcTemplate = j;
    }

    public List<ProdutoModel> listarProdutos() throws DataAccessException{
        String consulta = "select *from produtos";

        return this.jdbcTemplate.query(consulta, new ProdutoRowMapper());
    }

    public ProdutoModel buscarProdutoPorID(long id) throws DataAccessException{
        String consulta = "select *from produtos where produtoid = ?";
        

        return this.jdbcTemplate.queryForObject(consulta, new ProdutoRowMapper(), id);

    }

    public void salvarProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "insert into produtos(nome, valorunitario, quantidade, ehunidademassa) values(?, ?, ?, ?)";

        this.jdbcTemplate.update(consulta, p.getNome(), p.getValor(), p.getQuantidade(), p.getEhUnidadeMassa());

    }

    public boolean atualizarNomeProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "update produtos set nome = ? where produtoid = ?";

        if(this.jdbcTemplate.update(consulta, p.getNome(),  p.getCodigo()) > 0) {
            return true;
        }

        return false;
    }

    public boolean atualizarValorProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "update produtos set valorunitario = ? where produtoid = ?";

        if(this.jdbcTemplate.update(consulta, p.getValor(),  p.getCodigo()) > 0) {
            return true;
        }

        return false;
        
    }

    public boolean atualizarQuantidadeProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "update produtos set quantidade = ? where produtoid = ?";
        
        if(this.jdbcTemplate.update(consulta, p.getQuantidade(),  p.getCodigo()) > 0) {
            return true;
        }

        return false;
    }

    public boolean deletar(Long id) throws DataAccessException{
        String consulta = "delete from produtos where produtoid = ?";
        
        if(this.jdbcTemplate.update(consulta, id) > 0) {
            return true;
        }

        return false;
    }

    // Classe interna para mapear o resultado da query
    private static class ProdutoRowMapper implements RowMapper<ProdutoModel> {
        public ProdutoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProdutoModel p = new ProdutoModel();
            p.setCodigo(rs.getLong("produtoid"));
            p.setNome(rs.getString("nome"));
            p.setValor(rs.getBigDecimal("valorunitario"));
            p.setQuantidade(rs.getBigDecimal("quantidade"));
            p.setEhUnidadeMassa(rs.getBoolean("ehunidademassa"));
            return p;
        }
    }


    
}
