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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class CompraController {
    private CompraService compraService;

    public CompraController(CompraService cs) {
        this.compraService = cs;
    }

    @PostMapping("/compra")
    public ResponseEntity<CompraDto> salvaCompra(@RequestBody @Valid CompraDto cdto) {
        CompraModel c = cdto.compraDtoToModel();

        if(this.compraService.salvaCompra(c)) {
            return ResponseEntity.status(HttpStatus.OK).body(CompraDto.compraModelToDto(c));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CompraDto.compraModelToDto(c));
    }

    @GetMapping("/compra")
    public ResponseEntity<List<CompraDto>> listarCompras() {
        List<CompraModel> lcm = this.compraService.listarCompras();

        if(lcm.isEmpty() || lcm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CompraDto.listCompraModelToListDto(lcm));
        }

        return ResponseEntity.status(HttpStatus.OK).body(CompraDto.listCompraModelToListDto(lcm));
    }

    @GetMapping("/compra/{id}")
    public ResponseEntity<CompraDto> buscaCompraPorId(@PathVariable(value ="id") long id) {
        CompraModel c = this.compraService.buscaCompraPorId(id);

        if(c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CompraDto.compraModelToDto(c));
        }

        return ResponseEntity.status(HttpStatus.OK).body(CompraDto.compraModelToDto(c));
    }

    @PutMapping("/compra-status/{id}")
    public ResponseEntity<CompraDto> atualizaStatus(@PathVariable(value ="id") long id, @RequestBody @Valid CompraDto cdto) {
        CompraModel c = this.compraService.buscaCompraPorId(id);

        if(c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CompraDto.compraModelToDto(c));
        }

        c = cdto.compraDtoToModel();
        c.setCodigo(id);

        if(this.compraService.atualizaStatus(c)) {
            return ResponseEntity.status(HttpStatus.OK).body(CompraDto.compraModelToDto(c));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CompraDto.compraModelToDto(c));

    }

    @PutMapping("/compra-tipo-pagamento/{id}")
    public ResponseEntity<CompraDto> atualizaTipoPagamento(@PathVariable(value ="id") long id, @RequestBody @Valid CompraDto cdto) {
        CompraModel c = this.compraService.buscaCompraPorId(id);

        if(c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CompraDto.compraModelToDto(c));
        }

        c = cdto.compraDtoToModel();
        c.setCodigo(id);

        if(this.compraService.atualizaTipoPagamento(c)) {
            return ResponseEntity.status(HttpStatus.OK).body(CompraDto.compraModelToDto(c));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CompraDto.compraModelToDto(c));

    }


    @PutMapping("/compra-valor-recebido/{id}")
    public ResponseEntity<CompraDto> atualizaValorRecebido(@PathVariable(value ="id") long id, @RequestBody @Valid CompraDto cdto) {
        CompraModel c = this.compraService.buscaCompraPorId(id);

        if(c == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CompraDto.compraModelToDto(c));
        }

        c = cdto.compraDtoToModel();
        c.setCodigo(id);

        if(this.compraService.atualizarValorRecebido(c)) {
            return ResponseEntity.status(HttpStatus.OK).body(CompraDto.compraModelToDto(c));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(CompraDto.compraModelToDto(c));

    }


    
}
