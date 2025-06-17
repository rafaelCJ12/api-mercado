package com.example.springBoot.dtos;

import java.math.BigDecimal;

import com.example.springBoot.models.CompraModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompraDto {
    @JsonProperty("responsavel")
    private long responsavel = 0;
    @JsonProperty("tipoPagmento")
    private long tipoPagamento = 0;
    @JsonProperty("valorRecebido")
    private BigDecimal valorRecebido;
    @JsonProperty("status")
    private long status = 0;

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

    public CompraModel compraDtoToModel() {
        CompraModel c = new CompraModel();

        c.setTipoPagamento(this.tipoPagamento);
        c.setResponsavel(this.responsavel);
        c.setStatus(this.status);
        c.setValorRecebido(this.valorRecebido);

        return c;
    }


    
}
