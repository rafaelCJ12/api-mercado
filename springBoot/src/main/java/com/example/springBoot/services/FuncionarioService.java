package com.example.springBoot.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springBoot.models.FuncionarioModel;
import com.example.springBoot.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {
    private FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository fr) {
        this.funcionarioRepository = fr;
    }

    public List<FuncionarioModel> listarFuncionarios() {
        try{
            return this.funcionarioRepository.listarFuncionarios();
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL de listar funcionarios incorreta.");
            return null;
        }
    }

    public FuncionarioModel buscarFuncionarioPorId(long id) {
        try{
            return this.funcionarioRepository.buscarFuncionarioPorID(id);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL de buscar funcionarios incorreta.");
            return null;
        }
    }

    public long retornaIdFuncionario(FuncionarioModel f) {
        try{
            return this.funcionarioRepository.retornaIdFuncionario(f);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL de buscar funcionarios incorreta.");
            return -1;
        }
    } 

    private boolean formatacaoValida(String numero) {
        int i = 0;
        int j = 0;
        char valores[] = {'0','1','2','3','4','5','6','7','8','9'};

        if(numero == null || numero.length() != 11) {
            return false;
        }

        for(i = 0; i < numero.length(); i++) {
            j = 0;

            while(j < 10 && numero.charAt(i) != valores[j]) {
                j++;
            }

            /*significa que encontrou um caracter que nao eh numerico*/
            if(j == 10) {
                return false;
            }

        }

        return true;

    }

    private boolean numeroCpfValido(String numero) {
        int i = 0;
        int j = 10;
        int soma = 0;

        if(!formatacaoValida(numero)) {
            return false;
        }
        

        for(i = 0; i < 9; i++) {
            soma += (numero.charAt(i) - '0') * j;
            j--;
        }

        if((soma % 11 == 0) || (soma % 11 == 1)) {
            if(numero.charAt(9) - '0' != 0) {
                return false;
            }
        }

        else if(11 - (soma % 11) != numero.charAt(9) - '0') {
            return false;
        }

        soma = 0;
        j = 10;

        for(i = 1; i < 10; i++) {
            soma += (numero.charAt(i) - '0') * j;
            j--;
        }

        if((soma % 11 == 0) || (soma % 11 == 1)) {
            if(numero.charAt(10) - '0' != 0) {
                return false;
            }
        }

        else if(11 - (soma % 11) != numero.charAt(10) - '0') {
            return false;
        }

        return true;
    }

    private boolean senhaValida(String s) {
        String valoresValidos = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@$&";
        int i = 0;
        int j = 0;

        if(s == null || s.length() < 4) {
            return false;
        }

        for(i = 0; i < s.length(); i++) {
            j = 0;

            while(j < valoresValidos.length() && s.charAt(i) != valoresValidos.charAt(j)) {
                j++;
            }

            if(j == valoresValidos.length()) {
                return false;
            }
        }

        return true;
    }

    public String stringHash(String s) {
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

        return null;

    }

    private boolean nomeValido(String s) {
        String valoresValidos = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int i = 0;
        int j = 0;

        if(s == null || s.length() < 4) {
            return false;
        }

        for(i = 0; i < s.length(); i++) {
            j = 0;

            while(j < valoresValidos.length() && s.charAt(i) != valoresValidos.charAt(j)) {
                j++;
            }

            if(j == valoresValidos.length()) {
                return false;
            }
        }

        return true;

    }

    public boolean salvarFuncionario(FuncionarioModel f) {

        if(this.nomeValido(f.getNome()) && this.numeroCpfValido(f.getCpf()) && this.senhaValida(f.getSenha())) {
            try{
                f.setSenha(this.stringHash(f.getSenha()));
                this.funcionarioRepository.salvarFuncionario(f);
                return true;
            }

            catch(Exception e) {
                System.out.println("ERRO: consulta SQL para salvar funcionario incorreta.");

            }
        }

        return false;
    }

    public boolean atualizarNomeFuncionario(FuncionarioModel f) {
        try{
            return this.nomeValido(f.getNome()) && this.funcionarioRepository.atualizarNomeFuncionario(f);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o nome do funcionario incorreta.");
        }

        return false;
    }

    public boolean atualizarCpfFuncionario(FuncionarioModel f) {
        try{
            return this.numeroCpfValido(f.getCpf()) && this.funcionarioRepository.atualizarCpfFuncionario(f);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o cpf do funcionario incorreta.");
        }

        return false;

    }

    public boolean atualizarSenhaFuncionario(FuncionarioModel f) {
        try{
            f.setSenha(stringHash(f.getSenha()));
            if(f.getSenha() != null) {
                return this.funcionarioRepository.atualizarSenhaFuncionario(f);
            }
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar a senha do funcionario incorreta.");
        }

        return false;
    }

    public boolean deletar(long id) {
        try{
            return this.funcionarioRepository.deletar(id);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o nome do funcionario incorreta.");
        }

        return false;

    }


    
}
