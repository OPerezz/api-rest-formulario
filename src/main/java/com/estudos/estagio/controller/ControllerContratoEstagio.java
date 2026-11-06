
package com.estudos.estagio.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.estudos.estagio.model.ContratoEstagio;
import com.estudos.estagio.service.ServiceContratoEstagio;

import com.estudos.estagio.dto.dtoContratoEstagio.DadosCadastroContratoEstagio;
import com.estudos.estagio.dto.dtoContratoEstagio.DadosAtualizacaoContratoEstagio;
import com.estudos.estagio.dto.dtoContratoEstagio.DadosListagemContratoEstagio;

@RestController
@RequestMapping("/contratos")
@RequiredArgsConstructor
public class ControllerContratoEstagio {

    private final ServiceContratoEstagio serviceContrato;

    @GetMapping
    public ResponseEntity<List<DadosListagemContratoEstagio>> listar(
            @RequestParam(required = false) Long idAluno,
            @RequestParam(required = false) Long idEmpresa) {

        if (idAluno != null) {
            return ResponseEntity.ok(
                serviceContrato.listarPorAluno(idAluno)
            );
        }

        if (idEmpresa != null) {
            return ResponseEntity.ok(
                serviceContrato.listarPorEmpresa(idEmpresa)
            );
        }

        return ResponseEntity.ok(serviceContrato.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosAtualizacaoContratoEstagio> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            serviceContrato.buscarParaEdicao(id)
        );
    }

    @PostMapping
    public ResponseEntity<Void> criar(
            @RequestBody @Valid DadosCadastroContratoEstagio dados) {

        ContratoEstagio contrato = serviceContrato.criar(dados);

        URI uri = URI.create("/contratos/" + contrato.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoContratoEstagio dados) {

        if (!id.equals(dados.id())) {
            return ResponseEntity.badRequest().build();
        }

        serviceContrato.atualizar(dados);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        serviceContrato.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
