package com.example.springBoot.models;

import java.math.BigDecimal;

public class ProdutoCompraModel {
    private long produto = 0;
    private long compra = 0;
    private String nomeProduto = "";
    private BigDecimal valorUnitario;
    private BigDecimal quantidade;

    public long getProduto() {
        return this.produto;
    }

    public void setProduto(long p) {
        this.produto = p;
    }

    public long getCompra() {
        return this.compra;
    }

    public void setCompra(long c) {
        this.compra = c;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public void setNomeProduto(String n) {
        this.nomeProduto = n;
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


    
}
