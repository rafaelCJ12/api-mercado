package com.example.springBoot.services;

import java.math.BigDecimal;
import com.example.springBoot.models.ProdutoModel;
import com.example.springBoot.repositories.ProdutoRepository;
import java.util.List;

import org.springframework.dao.DataAccessException;
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

        catch(DataAccessException d) {
            System.out.println("ERRO: consulta SQL de listar produtos incorreta.");
            return null;
        }

    }

    public ProdutoModel buscarPorId(Long id) {
        try{
            return this.produtoRepository.buscarProdutoPorID(id);

        }

        catch(DataAccessException d){
            System.out.println("ERRO: consulta SQL para busca por id incorreta.");
            return null;
        }
    }

    private boolean nomeProdutoValido(ProdutoModel p) {
        if(p.getNome() == null || p.getNome().length() <= 0) {
            return false;
        }
        return true;
    }

    private boolean valorProdutoValido(ProdutoModel p) {
        if(p.getValor() == null || p.getValor().compareTo(new BigDecimal(0)) < 0) {
            return false;
        }
        return true;
    }

    private boolean quantidadeProdutoValida(ProdutoModel p) {
        if(p.getQuantidade() == null || p.getQuantidade().compareTo(new BigDecimal(0)) < 0) {
            return false;
        }

        return true;
    }

    public boolean salvarProduto(ProdutoModel p) {
        if(this.nomeProdutoValido(p) && this.valorProdutoValido(p) && this.quantidadeProdutoValida(p)) {
            try{
                this.produtoRepository.salvarProduto(p);
                return true;
            }

            catch(DataAccessException d) {
                System.out.println("ERRO: consulta SQL para salvar produto incorreta.");
                return false;
            }
        }
        return false;
    }

    public boolean atualizarNomeProduto(ProdutoModel p) {
        try{
            if(this.nomeProdutoValido(p) && this.produtoRepository.atualizarNomeProduto(p)) {
                return true;
            }
        }

        catch(DataAccessException d) {
            System.out.println("ERRO: consulta SQL para atualizar nome incorreta.");
            return false;
        }

        return false;
    }

    public boolean atualizarValorProduto(ProdutoModel p) {
        try{
            if(this.valorProdutoValido(p) && this.produtoRepository.atualizarValorProduto(p)) {
                return true;
            }
        }

        catch(DataAccessException d) {
            System.out.println("ERRO: consulta SQL para atualizar o valor do produto incorreta.");
            return false;
        }

        return false;
    }

    public boolean atualizarQuantidadeProduto(ProdutoModel p) {
        try{
            if(this.quantidadeProdutoValida(p) && this.produtoRepository.atualizarQuantidadeProduto(p)) {
                return true;
            }
        }

        catch(DataAccessException d) {
            System.out.println("ERRO: consulta SQL para atualizar a quantidade do produto incorreta.");
            return false;
        }

        return false;
    }

    public boolean deletar(long id) {
        try{
            return this.produtoRepository.deletar(id);
        }

        catch(DataAccessException d) {
            System.out.println("ERRO: consulta SQL para deletar produto incorreta.");
            return false;
        }

    }



}