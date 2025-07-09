package com.example.springBoot.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    public List<ProdutoModel> listarProdutos(int limit, int offset) throws DataAccessException{
        String consulta = "select *from produtos order by produtoid limit ? offset ? * ?";

        return this.jdbcTemplate.query(consulta, new ProdutoRowMapper(), limit, offset, limit);
    }

    public ProdutoModel buscarProdutoPorID(long id) throws DataAccessException{
        String consulta = "select *from produtos where produtoid = ?";
        

        return this.jdbcTemplate.queryForObject(consulta, new ProdutoRowMapper(), id);

    }

    public List<ProdutoModel> listaProdutosDaCompra(long idCompra) throws DataAccessException {
        String consulta = "select produtos.produtoid, produtos.nome, produtos.valorunitario, "+
        "produtos.quantidade, produtos.ehunidademassa from produtos inner join produto_compra on "+
        "produtos.produtoid = produto_compra.fkproduto inner join compras on " + 
        "compras.compraid = produto_compra.fkcompra where compras.compraid = ? order by produtos.produtoid";

        return this.jdbcTemplate.query(consulta, new ProdutoRowMapper(), idCompra);
    }

    @Transactional
    public void salvarProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "insert into produtos(nome, valorunitario, quantidade, ehunidademassa) values(?, ?, ?, ?)";

        this.jdbcTemplate.update(consulta, p.getNome(), p.getValor(), p.getQuantidade(), p.getEhUnidadeMassa());

    }

    @Transactional
    public boolean atualizarNomeProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "update produtos set nome = ? where produtoid = ?";

        return this.jdbcTemplate.update(consulta, p.getNome(),  p.getCodigo()) > 0;
    }

    @Transactional
    public boolean atualizarValorProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "update produtos set valorunitario = ? where produtoid = ?";

        return this.jdbcTemplate.update(consulta, p.getValor(),  p.getCodigo()) > 0;
        
    }

    @Transactional
    public boolean atualizarQuantidadeProduto(ProdutoModel p) throws DataAccessException{
        String consulta = "update produtos set quantidade = ? where produtoid = ?";
        
        return this.jdbcTemplate.update(consulta, p.getQuantidade(),  p.getCodigo()) > 0;
    }

    public boolean deletar(Long id) throws DataAccessException{
        String consulta = "delete from produtos where produtoid = ?";
        
        return this.jdbcTemplate.update(consulta, id) > 0;
    }

    // Classe interna para mapear o resultado da query
    private static class ProdutoRowMapper implements RowMapper<ProdutoModel> {
        public ProdutoModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
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
