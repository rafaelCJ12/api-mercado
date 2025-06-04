package com.example.springBoot.dtos;

import java.math.BigDecimal;
import com.example.springBoot.models.ProdutoModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoDto {
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("valorunitario")
    private double valorUnitario;
    @JsonProperty("quantidade")
    private double quantidade;
    @JsonProperty("ehunidademassa")
    private boolean ehUnidadeMassa;

   
    public String getNome() {
        return this.nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public double getValor() {
        return this.valorUnitario;
    }

    public void setValor(double v) {
        this.valorUnitario = v;
    }

    public double getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(double q) {
        this.quantidade = q;
    }

    public Boolean getEhUnidadeMassa() {
        return this.ehUnidadeMassa;
    }

    public void setEhUnidadeMassa(Boolean b) {
        this.ehUnidadeMassa = b;
    }

    public ProdutoModel produtoDtoToModel() {
        ProdutoModel p = new ProdutoModel();

        p.setNome(this.nome);
        p.setValor(new BigDecimal(this.valorUnitario));
        p.setQuantidade(new BigDecimal(this.quantidade));
        p.setEhUnidadeMassa(this.ehUnidadeMassa);

        return p;
    }


}
