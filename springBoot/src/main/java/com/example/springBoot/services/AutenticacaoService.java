package com.example.springBoot.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.example.springBoot.components.JwtUtil;
import com.example.springBoot.models.FuncionarioModel;
import com.example.springBoot.repositories.FuncionarioRepository;

@Service
public class AutenticacaoService {
    private FuncionarioRepository funcionarioRepository;
    private JwtUtil jwtUtil;

    public AutenticacaoService(FuncionarioRepository fr, JwtUtil ju) {
        this.funcionarioRepository = fr;
        this.jwtUtil = ju;
    }

    private String stringHash(String s) {
        MessageDigest md = null;
        byte[] bytes = null;
        StringBuilder hexString = null;

        try{
            md = MessageDigest.getInstance("SHA3-256");
            bytes = md.digest(s.getBytes(StandardCharsets.UTF_8));
            hexString = new StringBuilder();

            for(byte b : bytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        }

        catch(Exception e) {
            System.out.println("ERRO: falha ao converter String para hash.");
        }

        return "";

    }

    public String autenticar(String cpf, String senha) {
        FuncionarioModel fm = new FuncionarioModel();

        fm.setCpf(cpf);
        fm.setSenha(this.stringHash(senha));

        try{
            if(this.funcionarioRepository.retornaIdFuncionario(fm) > 0) {
                System.out.println(jwtUtil.gerarToken(fm.getCpf()));
                return jwtUtil.gerarToken(fm.getCpf());
            }

        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para retornar o id do funcionario incorreta.");
        }

        return null;
        
    }
    
}
