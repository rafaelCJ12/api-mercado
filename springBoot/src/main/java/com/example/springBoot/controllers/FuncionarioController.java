package com.example.springBoot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.example.springBoot.dtos.FuncionarioDto;
import com.example.springBoot.models.FuncionarioModel;
import com.example.springBoot.services.FuncionarioService;

@RestController
@RequestMapping("/api")
public class FuncionarioController {
    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService fs) {
        this.funcionarioService = fs;
    }


    @PostMapping("/adicionar-novo-funcionario")
    public ResponseEntity<FuncionarioModel> salvarFuncionario(@RequestBody @Valid FuncionarioDto fdto) {
        FuncionarioModel f = fdto.funcionarioDtoToModel();

        if(this.funcionarioService.salvarFuncionario(f)) {
            return ResponseEntity.status(HttpStatus.OK).body(f);
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(f);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid FuncionarioDto fdto) {
        FuncionarioModel f  = fdto.funcionarioDtoToModel();

        if(this.funcionarioService.retornaIdFuncionario(f) > 0) {
            return ResponseEntity.status(HttpStatus.OK).body("Login bem sucedido.");
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("ERRO: dados incorretos.");
    }

    @PostMapping("/atualizar-dados/{id}")
    public ResponseEntity<String> atualizarDados(@PathVariable(value ="id") long id, @RequestBody @Valid FuncionarioDto fdto) {
        FuncionarioModel f = this.funcionarioService.buscarFuncionarioPorId(id);

        if(f == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("ERRO: nao foi possivel atualizar os dados do funcionario, pois ele nao existe na base de dados.");
        }

        f = fdto.funcionarioDtoToModel();
        f.setCodigo(id);

        if(this.funcionarioService.atualizarCpfFuncionario(f) && this.funcionarioService.atualizarNomeFuncionario(f) && this.funcionarioService.atualizarSenhaFuncionario(f)) {
            return ResponseEntity.status(HttpStatus.OK).body("Atualizacao de dados bem sucedida.");
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("ERRO: nao foi possivel atualizar os dados do funcionario, dados invalidos.");

    }
    
}
