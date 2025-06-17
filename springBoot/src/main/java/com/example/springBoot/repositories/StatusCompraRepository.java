package com.example.springBoot.repositories;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StatusCompraRepository {
    private JdbcTemplate jdbcTemplate;

    public StatusCompraRepository(JdbcTemplate j) {
        this.jdbcTemplate = j;
    }

    public List<Long> listaTiposStatus() throws DataAccessException{
        String consulta = "select statusid from tipos_pagamento";

        return this.jdbcTemplate.queryForList(consulta, Long.class);
    }
    
}
