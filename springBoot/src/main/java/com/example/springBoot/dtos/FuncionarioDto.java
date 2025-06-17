package com.example.springBoot.dtos;

import com.example.springBoot.models.FuncionarioModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FuncionarioDto {

    @JsonProperty("nome")
    String nome;
    @JsonProperty("cpf")
    String cpf;
    @JsonProperty("senha")
    String senha;
    @JsonProperty("tipo")
    long tipo;

    public String getNome() {
        return this.nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public String getCpf() {
        return this.nome;
    }

    public void setCpf(String c) {
        this.nome = c;
    }

    public String getSenha() {
        return this.nome;
    }

    public void setSenha(String s) {
        this.nome = s;
    }

    public long getTipo() {
        return this.tipo;
    }

    public void setTipo(long t) {
        this.tipo = t;
    }

    public FuncionarioModel funcionarioDtoToModel() {
        FuncionarioModel fm = new FuncionarioModel();

        fm.setNome(this.nome);
        fm.setCpf(this.cpf);
        fm.setSenha(this.senha);
        fm.setTipo(this.tipo);

        return fm;
    }
    
}
