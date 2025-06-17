package com.example.springBoot.dtos;

import java.math.BigDecimal;
import com.example.springBoot.models.ProdutoModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoDto {
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("valorunitario")
    private BigDecimal valorUnitario;
    @JsonProperty("quantidade")
    private BigDecimal quantidade;
    @JsonProperty("ehunidademassa")
    private boolean ehUnidadeMassa;

   
    public String getNome() {
        return this.nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public BigDecimal getValor() {
        return this.valorUnitario;
    }

    public void setValor(BigDecimal v) {
        this.valorUnitario = v;
    }

    public BigDecimal getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(BigDecimal q) {
        this.quantidade = q;
    }

    public Boolean getEhUnidadeMassa() {
        return this.ehUnidadeMassa;
    }

    public void setEhUnidadeMassa(boolean b) {
        this.ehUnidadeMassa = b;
    }

    public ProdutoModel produtoDtoToModel() {
        ProdutoModel p = new ProdutoModel();

        p.setNome(this.nome);
        p.setValor(this.valorUnitario);
        p.setQuantidade(this.getQuantidade());
        p.setEhUnidadeMassa(this.ehUnidadeMassa);

        return p;
    }


}
