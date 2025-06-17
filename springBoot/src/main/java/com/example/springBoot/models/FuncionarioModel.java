package com.example.springBoot.models;

public class FuncionarioModel {
    private long codigo;
    private String nome;
    private String cpf;
    private String senha;
    private long tipo;

    public long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(long c) {
        this.codigo = c;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String n) {
        this.nome = n;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String c) {
        this.cpf = c;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String s) {
        this.senha = s;
    }

    public long getTipo() {
        return this.tipo;
    }

    public void setTipo(long t) {
        this.tipo = t;
    }




    
}
