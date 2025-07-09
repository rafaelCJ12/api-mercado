package com.example.springBoot.controllers;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.example.springBoot.dtos.ProdutoCompraDto;

import com.example.springBoot.models.ProdutoCompraModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springBoot.services.ProdutoCompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProdutoCompraController {
    private ProdutoCompraService produtoCompraService;

    public ProdutoCompraController(ProdutoCompraService pcs) {
        this.produtoCompraService = pcs;
    }

    @PostMapping("/produto-compra")
    public ResponseEntity<ProdutoCompraDto> salvarProdutoEmCompra(@RequestBody @Valid ProdutoCompraDto pcdto) {
        ProdutoCompraModel pcm = pcdto.produtoCompraDtoToModel();

        if(this.produtoCompraService.salvarProdutoEmCompra(pcm)) {
            return ResponseEntity.status(HttpStatus.OK).body(ProdutoCompraDto.produtoCompraModelToDto(pcm));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ProdutoCompraDto.produtoCompraModelToDto(pcm));
    }

    @GetMapping("/produtos-compra/compra={idcompra}?limit={l}&offset={o}")
    public ResponseEntity<List<ProdutoCompraDto>> retornaProdutoCompra(@PathVariable(value = "idcompra") 
    long compra, @PathVariable(value = "l") int limit, @PathVariable(value = "o") int offset) {
        List<ProdutoCompraModel> lPcm = this.produtoCompraService.listaProdutoCompra(compra, limit, offset);

        if(lPcm.isEmpty() || lPcm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProdutoCompraDto.listProdutoCompraModelToListDto(lPcm));
        }

        return ResponseEntity.status(HttpStatus.OK).body(ProdutoCompraDto.listProdutoCompraModelToListDto(lPcm));
    }

    @PutMapping("/produto-compra")
    public ResponseEntity<Map<String, String>> atualizarQuantidade(@RequestBody @Valid ProdutoCompraDto pcdto) {
        ProdutoCompraModel pcm = pcdto.produtoCompraDtoToModel();
        
        if(this.produtoCompraService.atualizarQuantidade(pcm)) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("STATUS", "Quantidade do produto atualizada."));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("STATUS", "Erro na atualizacao da quantidade do produto."));

    }

    @GetMapping("/valor-compra/{id}")
    public ResponseEntity<Map<String, BigDecimal>> totalDaCompra(@PathVariable(value ="id") long id) {
        BigDecimal total = this.produtoCompraService.retornaTotalCompra(id);

        if(total == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("status", total));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("total", total));
    }

    @DeleteMapping("/produto-compra/{idCompra}&{idProduto}")
    public ResponseEntity<Map<String, String>> deletarProdutoDaCompra(@PathVariable(value = "idCompra") long
    idCompra, @PathVariable(value = "idProduto") long idProduto) {
        if(this.produtoCompraService.deletarProdutoDaCompra(idCompra, idProduto)) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("STATUS", "Quantidade do produto atualizada."));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("STATUS", 
        "Erro ao deletar o produto da compra."));
    }
    
}
