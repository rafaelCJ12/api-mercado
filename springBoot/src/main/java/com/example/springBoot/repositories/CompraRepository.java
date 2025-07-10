package com.example.springBoot.repositories;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.example.springBoot.models.CompraModel;


@Repository
public class CompraRepository {

    private JdbcTemplate jdbcTemplate;

    public CompraRepository(JdbcTemplate j) {
        this.jdbcTemplate = j;
    }

    public List<CompraModel> listarCompras() throws DataAccessException{
        String consulta = "select *from compras";

        return this.jdbcTemplate.query(consulta, new CompraRowMapper());
    }

    public CompraModel buscaCompraPorId(long id) throws DataAccessException {
        String consulta = "select *from compras where compraid = ?";
    
        return this.jdbcTemplate.queryForObject(consulta, new CompraRowMapper(), id);

    }

    public void salvaCompra(CompraModel c) throws DataAccessException{
        String consulta = "insert into compras(fkfuncresponsavel, valorrecebido, fktipopag, fkstatus, datahora) values(?, ?, ?, ?, ?)";

        this.jdbcTemplate.update(consulta, c.getResponsavel(), c.getValorRecebido(), c.getTipoPagamento(), c.getStatus(), c.getDataHora());
    }

    public boolean atualizaValorRecebido(CompraModel c) throws DataAccessException {
        String consulta = "update compras set valorrecebido = ? where compraid = ?";

        return this.jdbcTemplate.update(consulta, c.getValorRecebido(), c.getCodigo()) > 0;
    }

    public boolean atualizaTipoPagamento(CompraModel c) throws DataAccessException {
        String consulta = "update compras set fktipopag = ? where compraid = ?";

        return this.jdbcTemplate.update(consulta, c.getValorRecebido(), c.getCodigo()) > 0;
    }

    public boolean atualizaStatus(CompraModel c) throws DataAccessException {
        String consulta = "update compras set fkstatus = ? where compraid = ?";

        return this.jdbcTemplate.update(consulta, c.getStatus(), c.getCodigo()) > 0;
    }

    public boolean atualizaDataHora(CompraModel c) throws DataAccessException {
        String consulta = "update compras set datahora = ? where compraid = ?";

        return this.jdbcTemplate.update(consulta, c.getDataHora(), c.getCodigo()) > 0;
    }

    public boolean deletar(Long id) throws DataAccessException{
        String consulta = "delete from compras where compraid = ?";
        
        return this.jdbcTemplate.update(consulta, id) > 0;
    }



    // Classe interna para mapear o resultado da query
    private static class CompraRowMapper implements RowMapper<CompraModel> {
        public CompraModel mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            CompraModel c = new CompraModel();
            Timestamp timestamp = rs.getTimestamp("datahora");
            
            c.setCodigo(rs.getLong("compraid"));
            c.setResponsavel(rs.getLong("fkfuncresponsavel"));
            c.setTipoPagamento(rs.getLong("fktipopag"));
            c.setValorRecebido(rs.getBigDecimal("valorrecebido"));
            c.setStatus(rs.getLong("fkstatus"));
            
            if(timestamp == null) {
                c.setDataHora(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));
            }
            else{
                c.setDataHora(timestamp.toLocalDateTime().atZone(ZoneId.of("America/Sao_Paulo")));

            }
    
            return c;
        }
    }
    
}
