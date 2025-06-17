package com.example.springBoot.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;


public class CompraModel {
    private long codigoCompra = 0;
    private long responsavel = 0;
    private long tipoPagamento = 0;
    private BigDecimal valorRecebido;
    private long status = 1;
    private ZonedDateTime dataHora = null;

    
    public long getCodigo() {
        return this.codigoCompra;
    }

    public void setCodigo(long c) {
        this.codigoCompra = c;
    }

    public long getResponsavel() {
        return this.responsavel;
    }

    public void setResponsavel(long r) {
        this.responsavel = r;
    }

    public long getTipoPagamento() {
        return this.tipoPagamento;
    }

    public void setTipoPagamento(long t) {
        this.tipoPagamento = t;
    }

    public BigDecimal getValorRecebido() {
        return this.valorRecebido;
    }

    public void setValorRecebido(BigDecimal v) {
        this.valorRecebido = v;
    }

    public long getStatus() {
        return this.status;
    }

    public void setStatus(long s) {
        this.status = s;
    }

    public ZonedDateTime getDataHora() {
        return this.dataHora;
    }

    public void setDataHora(ZonedDateTime z) {
        this.dataHora = z;
    }
   
}
