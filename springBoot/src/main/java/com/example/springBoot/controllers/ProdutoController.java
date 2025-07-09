package com.example.springBoot.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springBoot.dtos.ProdutoDto;
import com.example.springBoot.models.ProdutoModel;
import com.example.springBoot.services.ProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ProdutoController {
    private ProdutoService produtoService;

    public ProdutoController(ProdutoService ps) {
        this.produtoService = ps;
    }

    @PostMapping("/adicionar-novo-produto")
    public ResponseEntity<ProdutoDto> salvaProduto(@RequestBody @Valid ProdutoDto pdto) {
        ProdutoModel p = pdto.produtoDtoToModel();

        if(this.produtoService.salvarProduto(p)) {
            return ResponseEntity.status(HttpStatus.OK).body(ProdutoDto.produtoModelToDto(p));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ProdutoDto.produtoModelToDto(p));
    }

    @GetMapping("/produtos?limit={l}&offset={o}")
    public ResponseEntity<List<ProdutoDto>> retornaProdutos(@PathVariable(value ="l") int limit, @PathVariable(value ="o") int offset) {
        List<ProdutoModel> lp = this.produtoService.listarProdutos(limit, offset);

        if(lp.isEmpty() || lp == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProdutoDto.listProdutoModelToListDto(lp));
        }

        return ResponseEntity.status(HttpStatus.OK).body(ProdutoDto.listProdutoModelToListDto(lp));
    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<ProdutoDto> retornaProduto(@PathVariable(value ="id") long id) {
        ProdutoModel p = this.produtoService.buscarPorId(id);

        if(p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProdutoDto.produtoModelToDto(p));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ProdutoDto.produtoModelToDto(p));

    }

    @PutMapping("/atualizar-produto/{id}")
    public ResponseEntity<ProdutoDto> atualizaProduto(@PathVariable(value ="id") long id, @RequestBody @Valid ProdutoDto pdto) {
        ProdutoModel p = this.produtoService.buscarPorId(id);

        if(p == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProdutoDto.produtoModelToDto(p));
        }

        p = pdto.produtoDtoToModel();
        p.setCodigo(id);

        if(this.produtoService.atualizarNomeProduto(p) && this.produtoService.atualizarQuantidadeProduto(p) && this.produtoService.atualizarValorProduto(p)){
            return ResponseEntity.status(HttpStatus.OK).body(ProdutoDto.produtoModelToDto(p));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ProdutoDto.produtoModelToDto(p));
        
    }





    
    
}
