package com.example.springBoot.services;

import java.math.BigDecimal;
import com.example.springBoot.models.ProdutoModel;
import com.example.springBoot.repositories.ProdutoRepository;
import java.util.List;


import org.springframework.stereotype.Service;

@Service
public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository pr) {
        this.produtoRepository = pr;
    }

    public List<ProdutoModel> listarProdutos() {
        try{
            return this.produtoRepository.listarProdutos();

        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL de listar produtos incorreta.");
            return null;
        }

    }

    public ProdutoModel buscarPorId(Long id) {
        try{
            return this.produtoRepository.buscarProdutoPorID(id);

        }

        catch(Exception e){
            System.out.println("ERRO: consulta SQL para busca por id incorreta.");
            return null;
        }
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



    private boolean valorValido(BigDecimal v) {
         if(v == null || v.compareTo(new BigDecimal("0.00")) < 0) {
            return false;
        }
        
        return true;
    }


    public boolean salvarProduto(ProdutoModel p) {
        if(this.nomeValido(p.getNome()) && this.valorValido(p.getValor()) && this.valorValido(p.getQuantidade())) {
            try{
                this.produtoRepository.salvarProduto(p);
                return true;
            }

            catch(Exception e) {
                System.out.println("ERRO: consulta SQL para salvar produto incorreta.");
            }
        }
        return false;
    }

    public boolean atualizarNomeProduto(ProdutoModel p) {
        try{
            return this.nomeValido(p.getNome()) && this.produtoRepository.atualizarNomeProduto(p);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar nome incorreta.");
        }

        return false;
    }

    public boolean atualizarValorProduto(ProdutoModel p) {
        try{
            return this.valorValido(p.getValor()) && this.produtoRepository.atualizarValorProduto(p);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o valor do produto incorreta.");
        }

        return false;
    }

    public boolean atualizarQuantidadeProduto(ProdutoModel p) {
        try{
             return this.valorValido(p.getQuantidade()) && this.produtoRepository.atualizarQuantidadeProduto(p);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar a quantidade do produto incorreta.");
        }

        return false;
    }

    public boolean deletar(long id) {
        try{
            return this.produtoRepository.deletar(id);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para deletar produto incorreta.");
        }

        return false;
    }





}