package com.example.springBoot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginDto {
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("senha")
    private String senha;

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


    
}
