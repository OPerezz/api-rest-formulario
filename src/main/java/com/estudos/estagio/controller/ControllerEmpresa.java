
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

import com.estudos.estagio.dto.dtoEmpresa.DadosAtualizacaoEmpresa;
import com.estudos.estagio.dto.dtoEmpresa.DadosCadastroEmpresa;
import com.estudos.estagio.dto.dtoEmpresa.DadosListagemEmpresa;
import com.estudos.estagio.model.Empresa;
import com.estudos.estagio.service.ServiceEmpresa;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/empresas")
@RequiredArgsConstructor
public class ControllerEmpresa {

    private final ServiceEmpresa serviceEmpresa;

    @GetMapping
    public ResponseEntity<List<DadosListagemEmpresa>> listar(
            @RequestParam(required = false) String nome) {

        return ResponseEntity.ok(serviceEmpresa.listar(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosAtualizacaoEmpresa> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(serviceEmpresa.buscarParaEdicao(id));
    }

    @PostMapping
    public ResponseEntity<Void> criar(
            @RequestBody @Valid DadosCadastroEmpresa dados) {

        Empresa empresa = serviceEmpresa.criar(dados);

        URI uri = URI.create("/empresas/" + empresa.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoEmpresa dados) {

        if (!id.equals(dados.id())) {
            return ResponseEntity.badRequest().build();
        }

        serviceEmpresa.atualizar(dados);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        serviceEmpresa.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
