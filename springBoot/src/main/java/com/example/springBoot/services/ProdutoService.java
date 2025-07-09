package com.example.springBoot.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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

    public List<ProdutoModel> listarProdutos(int limit, int offset) {
        try{
            return this.produtoRepository.listarProdutos(limit, offset);

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
        String valoresValidos = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
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
         if(v == null || v.compareTo(new BigDecimal("0.00", new MathContext(3, RoundingMode.HALF_UP))) < 0) {
            return false;
        }
        
        return true;
    }

    private ProdutoModel regulaValor(ProdutoModel p) {
        if(p.getEhUnidadeMassa()) {
            p.setQuantidade(p.getQuantidade().round(new MathContext(4, RoundingMode.HALF_UP)));
        }

        else{
             p.setQuantidade(p.getQuantidade().round(new MathContext(1, RoundingMode.HALF_UP)));
        }

        return p;
    }


    public boolean salvarProduto(ProdutoModel p) {
        if(this.nomeValido(p.getNome()) && this.valorValido(p.getValor()) && this.valorValido(p.getQuantidade())) {
            try{
                p = this.regulaValor(p);
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
            if(this.valorValido(p.getValor())) {
                return this.produtoRepository.atualizarValorProduto(p);
            }
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o valor do produto incorreta.");
        }

        return false;
    }

    public boolean atualizarQuantidadeProduto(ProdutoModel p) {
        try{
            if(this.valorValido(p.getQuantidade())) {
                p = this.regulaValor(p);
                return this.produtoRepository.atualizarQuantidadeProduto(p);
            }
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