
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

import com.estudos.estagio.dto.dtoAluno.DadosAtualizacaoAluno;
import com.estudos.estagio.dto.dtoAluno.DadosCadastroAluno;
import com.estudos.estagio.dto.dtoAluno.DadosListagemAluno;
import com.estudos.estagio.model.Aluno;
import com.estudos.estagio.service.ServiceAluno;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class ControllerAluno {

    private final ServiceAluno serviceAluno;

    @GetMapping
    public ResponseEntity<List<DadosListagemAluno>> listar(
            @RequestParam(required = false) String nome) {

        return ResponseEntity.ok(serviceAluno.listar(nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosAtualizacaoAluno> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(serviceAluno.buscarParaEdicao(id));
    }

    @PostMapping
    public ResponseEntity<Void> criar(
            @RequestBody @Valid DadosCadastroAluno dados) {

        Aluno aluno = serviceAluno.criar(dados);

        URI uri = URI.create("/alunos/" + aluno.getId());

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosAtualizacaoAluno dados) {

        if (!id.equals(dados.id())) {
            return ResponseEntity.badRequest().build();
        }

        serviceAluno.atualizar(dados);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long id) {

        serviceAluno.deletar(id);

        return ResponseEntity.noContent().build();
    }
}
