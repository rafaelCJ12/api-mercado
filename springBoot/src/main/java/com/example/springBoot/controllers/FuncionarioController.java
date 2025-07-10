package com.example.springBoot.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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


    @PostMapping("/funcionario")
    public ResponseEntity<Map<String, String>> salvarFuncionario(@RequestBody @Valid FuncionarioDto fdto) {
        FuncionarioModel f = fdto.funcionarioDtoToModel();

        if(this.funcionarioService.salvarFuncionario(f)) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("STATUS", "Cadastro de novo funcionario realizado com sucesso."));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("STATUS", "Erro ao cadastrar novo funcionario."));
    }


    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<Map<String, String>> atualizarDados(@PathVariable(value ="id") long id, @RequestBody @Valid FuncionarioDto fdto) {
        FuncionarioModel f = this.funcionarioService.buscarFuncionarioPorId(id);

        if(f == null) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("STATUS", "Erro ao atualizar dados do funcionario (usuario nao encontrado)."));
        }

        f = fdto.funcionarioDtoToModel();
        f.setCodigo(id);

        if(this.funcionarioService.atualizarCpfFuncionario(f) && this.funcionarioService.atualizarNomeFuncionario(f) && this.funcionarioService.atualizarSenhaFuncionario(f)) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("STATUS", "Dados do funcionario atualizados com sucesso."));
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("STATUS", "Erro ao atualizar dados do funcionario."));

    }
    
}
