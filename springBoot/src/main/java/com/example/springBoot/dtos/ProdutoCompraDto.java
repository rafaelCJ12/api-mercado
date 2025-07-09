package com.example.springBoot.dtos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.example.springBoot.models.ProdutoCompraModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoCompraDto {
    @JsonProperty("idProduto")
    private long produto;
    @JsonProperty("idCompra")
    private long compra;
    @JsonProperty("quantidadeProduto")
    private BigDecimal quantidade;

    public long getProduto() {
        return this.produto;
    }

    public void setProduto(long p) {
        this.produto = p;
    }

    public long getCompra() {
        return this.compra;
    }

    public void setCompra(long c) {
        this.compra = c;
    }

    public BigDecimal getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(BigDecimal q) {
        this.quantidade = q;
    }

    public ProdutoCompraModel produtoCompraDtoToModel() {
        ProdutoCompraModel pcm = new ProdutoCompraModel();

        pcm.setCompra(this.compra);
        pcm.setProduto(this.produto);
        pcm.setQuantidade(this.quantidade);
        pcm.setValor(new BigDecimal("0"));

        return pcm;
    }

    public static ProdutoCompraDto produtoCompraModelToDto(ProdutoCompraModel pcm) {
        ProdutoCompraDto pcdto = new ProdutoCompraDto();

        if(pcm == null) {
            return pcdto;
        }

        pcdto.setCompra(pcm.getCompra());
        pcdto.setProduto(pcm.getProduto());
        pcdto.setQuantidade(pcm.getQuantidade());

        return pcdto;
    }

    public static List<ProdutoCompraDto> listProdutoCompraModelToListDto(List<ProdutoCompraModel> lPcm) {
        List<ProdutoCompraDto> lPcd = new LinkedList<>();
        int i = 0;

        if(lPcm == null) {
            return lPcd;
        }

        for(i = 0; i < lPcm.size(); i++) {
            lPcd.add(ProdutoCompraDto.produtoCompraModelToDto(lPcm.get(i)));
        }

        return lPcd;

    }
    
}
