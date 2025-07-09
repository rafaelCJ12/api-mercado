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

import com.example.springBoot.models.ProdutoCompraModel;

@Repository
public class ProdutoCompraRepository {

    private JdbcTemplate jdbcTemplate;

    public ProdutoCompraRepository(JdbcTemplate j) {
        this.jdbcTemplate = j;
    }

    public List<ProdutoCompraModel> listaProdutoCompra(long idCompra, int limit, int offset) throws DataAccessException{
        String consulta = "select compras.compraid as codigocompra, produtos.produtoid as codigoproduto, " +
        "produtos.nome as nomeproduto, produto_compra.valorunitario, produto_compra.quantidade from " +
        "produto_compra inner join produtos on produto_compra.fkproduto = produtos.produtoid inner join " +
        "compras on compras.compraid = produto_compra.fkcompra where compras.compraid  = ? " +
        "order by codigoproduto limit ? offset ? * ?";

        return this.jdbcTemplate.query(consulta, new ProdutoCompraRowMapper(), idCompra, limit, offset, limit);
    }

    public List<ProdutoCompraModel> listaProdutoCompra(long idCompra) {
        String consulta = "select compras.compraid as codigocompra, produtos.produtoid as codigoproduto, " +
        "produtos.nome as nomeproduto, produto_compra.valorunitario, produto_compra.quantidade from " +
        "produto_compra inner join produtos on produto_compra.fkproduto = produtos.produtoid inner join " +
        "compras on compras.compraid = produto_compra.fkcompra where compras.compraid  = ? order by " +
        "codigoproduto";

        return this.jdbcTemplate.query(consulta, new ProdutoCompraRowMapper(), idCompra);
    }

    @Transactional
    public void salvarProdutoEmCompra(ProdutoCompraModel p) throws DataAccessException{
        String consulta = "inset into produto_compra(fkproduto, fkcompra, valorunitario, quantidade) " +
        "values(?, ?, ?, ?)";

        this.jdbcTemplate.update(consulta, p.getProduto(), p.getCompra(), p.getValor(), p.getQuantidade());
    }

    @Transactional
    public boolean atualizarQuantidade(ProdutoCompraModel p) throws DataAccessException{
        String consulta = "update produto_compra set quantidade = ? where fkproduto = ? and fkcompra = ?";

        return this.jdbcTemplate.update(consulta, p.getQuantidade(), p.getProduto(), p.getCompra()) > 0;
    }

    @Transactional
    public boolean deletarProdutoDaCompra(long idCompra, long idProduto) throws DataAccessException {
        String consulta = "delete from produto_compra where fkproduto = ? and fkcompra = ?";

        return this.jdbcTemplate.update(consulta, idProduto, idCompra) > 0;
    }

    private static class ProdutoCompraRowMapper implements RowMapper<ProdutoCompraModel> {
        public ProdutoCompraModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException{
            ProdutoCompraModel pcm = new ProdutoCompraModel();

            pcm.setCompra(rs.getLong("codigocompra"));
            pcm.setProduto(rs.getLong("codigoproduto"));
            pcm.setNomeProduto(rs.getString("nomeproduto"));
            pcm.setValor(rs.getBigDecimal("valorunitario"));
            pcm.setQuantidade(rs.getBigDecimal("quantidade"));

            return pcm;


        }
    }
    
}
