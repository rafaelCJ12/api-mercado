package com.example.springBoot.controllers;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import com.example.springBoot.dtos.CompraDto;
import com.example.springBoot.models.CompraModel;
import com.example.springBoot.services.CompraService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class CompraController {
    private CompraService compraService;

    public CompraController(CompraService cs) {
        this.compraService = cs;
    }

    @PostMapping("/criar-nova-compra")
    public ResponseEntity<CompraModel> salvaCompra(@RequestBody @Valid CompraDto cdto) {
        CompraModel c = cdto.compraDtoToModel();

        if(this.compraService.salvaCompra(c)) {
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(c);
    }

    @GetMapping("/compras")
    public ResponseEntity<List<CompraModel>> listarCompras() {
        List<CompraModel> lcm = this.compraService.listarCompras();

        if(lcm.isEmpty() || lcm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(lcm);
        }

        return ResponseEntity.status(HttpStatus.OK).body(lcm);
    }

    @GetMapping("/compra/{id}")
    public ResponseEntity<CompraModel> buscaCompraPorId(@PathVariable(value ="id") long id) {
        CompraModel c = this.compraService.buscaCompraPorId(id);

        if(c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(c);
        }

        return ResponseEntity.status(HttpStatus.OK).body(c);
    }

    @PostMapping("/atualiza-compra/{id}")
    public ResponseEntity<CompraModel> atualizaCompra(@PathVariable(value ="id") long id, @RequestBody @Valid CompraDto cdto) {
        CompraModel c = this.compraService.buscaCompraPorId(id);

        if(c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(c);
        }

        c = cdto.compraDtoToModel();

        c.setCodigo(id);
        c.setDataHora(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")));

        if(this.compraService.atualizaStatus(c) && this.compraService.atualizaTipoPagamento(c) && this.compraService.atualizarValorRecebido(c)) {
            return ResponseEntity.status(HttpStatus.OK).body(c);
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(c);

    }


    
}
