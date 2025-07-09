package com.example.springBoot.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springBoot.models.CompraModel;
import com.example.springBoot.models.ProdutoCompraModel;
import com.example.springBoot.models.ProdutoModel;
import com.example.springBoot.repositories.CompraRepository;
import com.example.springBoot.repositories.ProdutoCompraRepository;
import com.example.springBoot.repositories.ProdutoRepository;

@Service
public class CompraService {
    private CompraRepository compraRepository;
    private ProdutoRepository produtoRepository;
    private ProdutoCompraRepository produtoCompraRepository;

    public CompraService(CompraRepository cr, ProdutoRepository pr, ProdutoCompraRepository pcr) {
        this.compraRepository = cr;
        this.produtoRepository = pr;
        this.produtoCompraRepository = pcr;
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
         if(v == null || v.compareTo(new BigDecimal("0.00", new MathContext(3, RoundingMode.HALF_UP))) < 0) {
            return false;
        }
        
        return true;
    }

    public boolean atualizarValorRecebido(CompraModel c) {
        try{
            if(this.valorValido(c.getValorRecebido()) && c.getStatus() == 1) {
                return this.compraRepository.atualizaValorRecebido(c);
            }
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o valor da compra incorreta.");
        }

        return false;

    }

    public boolean atualizaTipoPagamento(CompraModel c) {
        try{
            if(c.getTipoPagamento() > 0 && c.getStatus() == 1) {
                return this.compraRepository.atualizaTipoPagamento(c);
            }
        }

        catch(Exception e) {
            System.out.println("ERRO: consulta SQL para atualizar o tipo de pagamento da compra incorreta.");
        }

        return false;
    }

    private void descontaProdutosDoEstoque(CompraModel c) {
        List<ProdutoModel> listaProdutos = null;
        List<ProdutoCompraModel> listaProdutoCompra = null;
        int i = 0;
        int j = 0;
        BigDecimal quantidade = null;

        try{
            listaProdutos = this.produtoRepository.listaProdutosDaCompra(c.getCodigo());
            listaProdutoCompra = this.produtoCompraRepository.listaProdutoCompra(c.getCodigo());

            if (listaProdutos == null || listaProdutoCompra == null) {
                return;
            }

            for(i = 0; i < listaProdutoCompra.size(); i++) {
                j = 0;

                while(j < listaProdutos.size() ) {
                    if(listaProdutoCompra.get(i).getProduto() == listaProdutos.get(j).getCodigo()) {
                        if(listaProdutos.get(j).getEhUnidadeMassa()) {
                            quantidade = listaProdutos.get(j).getQuantidade().subtract(listaProdutoCompra.
                            get(i).getQuantidade(), new MathContext(4, RoundingMode.HALF_UP));
                        }

                        else{
                            quantidade = listaProdutos.get(j).getQuantidade().subtract(listaProdutoCompra.
                            get(i).getQuantidade(), new MathContext(1, RoundingMode.HALF_UP));
                        }

                        listaProdutos.get(j).setQuantidade(quantidade);

                        this.produtoRepository.atualizarQuantidadeProduto(listaProdutos.get(j));

                        j = listaProdutos.size();
                    }
                    j++;
                }

            }

        }

        catch(Exception e) {
            System.out.println("ERRO: lancado em algum lugar do metodo de descontar a " +   
            "quantidade de produto do estoque");

        }

    }


    public boolean atualizaStatus(CompraModel c) {
        long st = 0;
        try{
            st = c.getStatus(); //guarda o status que se quer atualizar
            c = this.compraRepository.buscaCompraPorId(c.getCodigo()); //busca a compra

            //verifica se o status atual da e compra = 1 e se o novo status eh valido
            if(c.getCodigo() == 1 && (st == 2 || st == 3)) {
                c.setCodigo(st);

                //se a compra for finalizada, deve-se descontar os produtos do estoque
                if(st == 2) {
                    this.descontaProdutosDoEstoque(c);
                }
                return this.atualizaStatus(c);
            }
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
