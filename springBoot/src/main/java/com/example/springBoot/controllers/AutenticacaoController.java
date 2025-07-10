package com.example.springBoot.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springBoot.dtos.LoginDto;
import com.example.springBoot.services.AutenticacaoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AutenticacaoController {
    private AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService as) {
        this.autenticacaoService = as;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid LoginDto ldto) {
        String token = this.autenticacaoService.autenticar(ldto.getCpf(), ldto.getSenha());

        System.out.println("Entrou no login.");

        if(token != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "erro"));
    }
    
}
