package com.example.springBoot.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springBoot.models.CompraModel;
import com.example.springBoot.repositories.CompraRepository;

@Service
public class CompraService {
    private CompraRepository compraRepository;

    public CompraService(CompraRepository cr) {
        this.compraRepository = cr;
    }

    public List<CompraModel> listarCompras() {
        try{
            return this.compraRepository.listarCompras();
        }

        catch(Exception e){
            System.out.println("ERRO: consulta SQL de listar compras incorreta.");
            return null;
        }
    }

    public CompraModel buscaCompraPorId(Long id) {
        try{
            return this.compraRepository.buscaCompraPorId(id);
        }

        catch(Exception e){
            System.out.println("ERRO: consulta SQL para busca por id incorreta.");
            return null;
        }
    }

    public boolean salvaCompra(CompraModel c) {
        try{
            this.compraRepository.salvaCompra(c);
            return true;
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta para salvar compra incorreta.");
        }

        return false;

    }

    private boolean valorValido(BigDecimal v) {
         if(v == null || v.compareTo(new BigDecimal("0.00")) < 0) {
            return false;
        }
        
        return true;
    }

    public boolean atualizarValorRecebido(CompraModel c) {
        try{
            return this.valorValido(c.getValorRecebido()) && this.compraRepository.atualizaValorRecebido(c) && c.getStatus() == 1;
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o valor da compra incorreta.");
        }

        return false;

    }

    public boolean atualizaTipoPagamento(CompraModel c) {
        try{
            return c.getTipoPagamento() > 0 && this.compraRepository.atualizaTipoPagamento(c) && c.getStatus() == 1;
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o tipo de pagamento da compra incorreta.");
        }

        return false;
    }

    public boolean atualizaStatus(CompraModel c) {
        try{
            return c.getStatus() == 1 && this.compraRepository.atualizaStatus(c);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o status da compra incorreta.");
        }

        return false;
    }

    public boolean deletar(long id) {
        try{
            return this.compraRepository.deletar(id);
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para deletar compra incorreta.");
            return false;
        }
    }


    
}
