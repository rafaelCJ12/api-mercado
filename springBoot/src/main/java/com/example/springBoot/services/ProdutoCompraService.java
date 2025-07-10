package com.example.springBoot.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.springBoot.models.CompraModel;
import com.example.springBoot.models.ProdutoCompraModel;
import com.example.springBoot.models.ProdutoModel;
import com.example.springBoot.repositories.CompraRepository;
import com.example.springBoot.repositories.ProdutoCompraRepository;
import com.example.springBoot.repositories.ProdutoRepository;

@Service
public class ProdutoCompraService {

    private ProdutoCompraRepository produtoCompraRepository;
    private ProdutoRepository produtoRepository;
    private CompraRepository compraRepository;

    public ProdutoCompraService(ProdutoCompraRepository pcr, ProdutoRepository pr, CompraRepository cr) {
        this.produtoCompraRepository = pcr;
        this.produtoRepository = pr;
        this.compraRepository = cr;
    }

    public List<ProdutoCompraModel> listaProdutoCompra(long idCompra, int limit, int offset) {
        List<ProdutoCompraModel> lPcm = null;
        int i = 0;
        try{
            lPcm = this.produtoCompraRepository.listaProdutoCompra(idCompra, limit, offset);
            for(i = 0; i < lPcm.size(); i++) {
                lPcm.get(i).setNomeProduto(this.nomeProduto(lPcm.get(i).getProduto()));
            }
            return lPcm;

        }

        catch(Exception e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + 
            " - " + e.getMessage());
            return null;

        }
    }

    private boolean validarProdutoNaCompra(ProdutoCompraModel pcm) throws DataAccessException{
        ProdutoModel pm = this.produtoRepository.buscarProdutoPorID(pcm.getProduto());
        CompraModel cm = this.compraRepository.buscaCompraPorId(pcm.getCompra());

        if(pm == null || cm == null) {
            return false;
        }

        if(pm.getQuantidade().compareTo(pcm.getQuantidade()) >= 0 && cm.getStatus() == 1) {
            return true;
        }
        
        return false;
    }

    private BigDecimal valorUnitarioDoProduto(long idProduto) throws DataAccessException{
        ProdutoModel pm = this.produtoRepository.buscarProdutoPorID(idProduto);

        if(pm == null) {
            return new BigDecimal("0.00");
        }

        return pm.getValor();
    }

    private String nomeProduto(long idProduto) throws DataAccessException{
        ProdutoModel pm = this.produtoRepository.buscarProdutoPorID(idProduto);

        if(pm == null) {
            return "";
        }

        return pm.getNome();
    }


    public boolean salvarProdutoEmCompra(ProdutoCompraModel p) {
        try{
            if(this.validarProdutoNaCompra(p)) {
                p.setValor(this.valorUnitarioDoProduto(p.getProduto()));
                //p.setNomeProduto(this.nomeProduto(p.getProduto()));
                this.produtoCompraRepository.salvarProdutoEmCompra(p);
                return true;
            }
        }

        catch(Exception e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + 
            " - " + e.getMessage());
            
        }
        return false;
    }

    public boolean atualizarQuantidade(ProdutoCompraModel p) {
        try{
            if(this.validarProdutoNaCompra(p)) {
                p.setValor(this.valorUnitarioDoProduto(p.getProduto()));
                //p.setNomeProduto(this.nomeProduto(p.getProduto()));
                return this.produtoCompraRepository.atualizarQuantidade(p);
            }

        }

        catch(Exception e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + 
            " - " + e.getMessage());
        }
        return false;
    }

    private List<ProdutoCompraModel> listaProdutoCompra(long idCompra) {
        try{
            return this.produtoCompraRepository.listaProdutoCompra(idCompra);

        }

        catch(Exception e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + 
            " - " + e.getMessage());

        }

        return null;
    }

    public BigDecimal retornaTotalCompra(long idCompra) {
        MathContext mc = new MathContext(4, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal("0.00", mc);
        List<ProdutoCompraModel> lPc = this.listaProdutoCompra(idCompra);
        int i = 0;

        if(lPc == null) {
            return null;
        }

        for(i = 0; i < lPc.size(); i++) {
            total = total.add(lPc.get(i).getQuantidade().multiply(lPc.get(i).getValor(), mc));
        }

        return total;
    }

    public boolean deletarProdutoDaCompra(long idCompra, long idProduto) {
        try{
            return this.produtoCompraRepository.deletarProdutoDaCompra(idCompra, idProduto);

        }

        catch(Exception e) {
            System.out.println("ERRO: " + e.getClass().getSimpleName() + 
            " - " + e.getMessage());
        }

        return false;
    }
    
}
