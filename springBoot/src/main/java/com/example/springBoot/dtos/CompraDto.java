package com.example.springBoot.dtos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

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

    public static CompraDto compraModelToDto(CompraModel cm) {
        CompraDto cdto = new CompraDto();

        if(cm == null) {
            return cdto;
        }

        cdto.setResponsavel(cm.getResponsavel());
        cdto.setStatus(cm.getStatus());
        cdto.setTipoPagamento(cm.getTipoPagamento());
        cdto.setValorRecebido(cm.getValorRecebido());

        return cdto;
    }

    public static List<CompraDto> listCompraModelToListDto(List<CompraModel> lCm) {
        List<CompraDto> lCdto = new LinkedList<>();
        int i = 0;

        if(lCm == null) {
            return lCdto;
        }

        for(i = 0; i < lCm.size(); i++) {
            lCdto.add(CompraDto.compraModelToDto(lCm.get(i)));

        }

        return lCdto;

    }


    
}
