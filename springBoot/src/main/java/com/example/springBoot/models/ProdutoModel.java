package com.example.springBoot.models;


import java.math.BigDecimal;

public class ProdutoModel {
    private long codigoProduto = 0;
    private String nome;
    private BigDecimal valorUnitario;
    private BigDecimal quantidade;
    private boolean ehUnidadeMassa;

    public long getCodigo() {
        return this.codigoProduto;
    }

    public void setCodigo(long c) {
        this.codigoProduto = c;
    }

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

    public void setEhUnidadeMassa(Boolean b) {
        this.ehUnidadeMassa = b;
    }
}
