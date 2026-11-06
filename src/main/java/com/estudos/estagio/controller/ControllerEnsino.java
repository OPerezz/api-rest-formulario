
package com.estudos.estagio.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estudos.estagio.dto.dtoEnsino.DadosAtualizacaoEnsino;
import com.estudos.estagio.dto.dtoEnsino.DadosCadastroEnsino;
import com.estudos.estagio.dto.dtoEnsino.DadosListagemEnsino;
import com.estudos.estagio.model.Ensino;
import com.estudos.estagio.service.ServiceEnsino;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ensinos")
@RequiredArgsConstructor
public class ControllerEnsino {

    private final ServiceEnsino serviceEnsino;

    @GetMapping
    public ResponseEntity<List<DadosListagemEnsino>> listar(
            @RequestParam(required = false) String nome) {

        return ResponseEntity.ok(serviceEnsino.listar(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosAtualizacaoEnsino> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            serviceEnsino.buscarParaEdicao(id)
        );
    }

    @PostMapping
    public ResponseEntity<Void> criar(
            @RequestBody @Valid DadosCadastroEnsino dados) {

        Ensino ensino = serviceEnsino.criar(dados);

        URI uri = URI.create("/ensinos/" + ensino.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoEnsino dados) {

        if (!id.equals(dados.id())) {
            return ResponseEntity.badRequest().build();
        }

        serviceEnsino.atualizar(dados);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        serviceEnsino.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
