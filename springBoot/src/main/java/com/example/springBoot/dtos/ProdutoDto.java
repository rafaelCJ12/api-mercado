package com.example.springBoot.dtos;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.example.springBoot.models.ProdutoModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoDto {
    @JsonProperty("codigo")
    private long codigo;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("valorunitario")
    private BigDecimal valorUnitario;
    @JsonProperty("quantidade")
    private BigDecimal quantidade;
    @JsonProperty("ehunidademassa")
    private boolean ehUnidadeMassa;

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

    public BigDecimal getValor() {
        return this.valorUnitario;
    }

    public void setValor(BigDecimal v) {
        this.valorUnitario = v;
    }

    public BigDecimal getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(BigDecimal q) {
        this.quantidade = q;
    }

    public Boolean getEhUnidadeMassa() {
        return this.ehUnidadeMassa;
    }

    public void setEhUnidadeMassa(boolean b) {
        this.ehUnidadeMassa = b;
    }

    public ProdutoModel produtoDtoToModel() {
        ProdutoModel p = new ProdutoModel();

        p.setNome(this.nome);
        p.setValor(this.valorUnitario);
        p.setQuantidade(this.getQuantidade());
        p.setEhUnidadeMassa(this.ehUnidadeMassa);

        return p;
    }

    public static ProdutoDto produtoModelToDto(ProdutoModel pm) {
        ProdutoDto pDto = new ProdutoDto();

        if(pm == null) {
            return pDto;
        }

        pDto.setCodigo(pm.getCodigo());
        pDto.setNome(pm.getNome());
        pDto.setValor(pm.getValor());
        pDto.setQuantidade(pm.getQuantidade());
        pDto.setEhUnidadeMassa(pm.getEhUnidadeMassa());

        return pDto;
    }

    public static List<ProdutoDto> listProdutoModelToListDto(List<ProdutoModel> lPm) {
        List<ProdutoDto> lPdto = new LinkedList<>();
        int i = 0;

        if(lPm == null) {
            return lPdto;
        }

        for(i = 0; i < lPm.size(); i++) {
            lPdto.add(ProdutoDto.produtoModelToDto(lPm.get(i)));
        }


        return lPdto;
    }


}
