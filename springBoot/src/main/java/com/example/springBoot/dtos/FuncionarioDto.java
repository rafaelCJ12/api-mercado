package com.example.springBoot.dtos;

import com.example.springBoot.models.FuncionarioModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FuncionarioDto {
    
    @JsonProperty("codigo")
    private long codigo;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("senha")
    private String senha;
    @JsonProperty("tipo")
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

    public FuncionarioModel funcionarioDtoToModel() {
        FuncionarioModel fm = new FuncionarioModel();

        fm.setNome(this.nome);
        fm.setCpf(this.cpf);
        fm.setSenha(this.senha);
        fm.setTipo(this.tipo);

        return fm;
    }

    public static FuncionarioDto funcionarioModelToDto(FuncionarioModel fm) {
        FuncionarioDto fDto = new FuncionarioDto();

        if(fm == null) {
            return fDto;
        }

        fDto.setCodigo(fm.getCodigo());
        fDto.setNome(fm.getNome());
        fDto.setCpf(fm.getCpf());
        fDto.setSenha(fm.getSenha());
        fDto.setTipo(fm.getTipo());

        return fDto;

    }
    
}
