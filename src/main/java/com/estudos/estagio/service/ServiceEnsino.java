
package com.estudos.estagio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudos.estagio.dto.dtoEnsino.DadosAtualizacaoEnsino;
import com.estudos.estagio.dto.dtoEnsino.DadosCadastroEnsino;
import com.estudos.estagio.dto.dtoEnsino.DadosListagemEnsino;
import com.estudos.estagio.mapper.MapperEnsino;
import com.estudos.estagio.model.Ensino;
import com.estudos.estagio.repository.RepositoryAluno;
import com.estudos.estagio.repository.RepositoryEnsino;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceEnsino {

    private final RepositoryEnsino repositoryEnsino;
    private final RepositoryAluno repositoryAluno;
    private final MapperEnsino mapperEnsino;

    @Transactional(readOnly = true)
    public List<DadosListagemEnsino> listar(String nome) {

        List<Ensino> ensinos;

        if (nome == null || nome.isBlank()) {
            ensinos = repositoryEnsino.findAll();
        } else {
            ensinos = repositoryEnsino
                .findByNomeContainingIgnoreCase(nome.trim());
        }

        return ensinos.stream()
                .map(mapperEnsino::toListagem)
                .toList();
    }

    @Transactional(readOnly = true)
    public Ensino buscarPorId(Long id) {

        return repositoryEnsino.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Instituição de ensino não encontrada: " + id
                    )
                );
    }

    @Transactional(readOnly = true)
    public DadosAtualizacaoEnsino buscarParaEdicao(Long id) {

        Ensino ensino = buscarPorId(id);

        return mapperEnsino.toAtualizacao(ensino);
    }

    @Transactional
    public Ensino criar(DadosCadastroEnsino dados) {

        String nome = dados.nome().trim();

        if (repositoryEnsino.existsByNomeIgnoreCase(nome)) {
            throw new IllegalStateException(
                "Já existe uma instituição de ensino com este nome."
            );
        }

        Ensino ensino = mapperEnsino.toEntity(dados);
        ensino.setNome(nome);

        return repositoryEnsino.save(ensino);
    }

    @Transactional
    public Ensino atualizar(DadosAtualizacaoEnsino dados) {

        Ensino ensino = buscarPorId(dados.id());

        String nome = dados.nome().trim();

        if (!ensino.getNome().equalsIgnoreCase(nome)
                && repositoryEnsino.existsByNomeIgnoreCase(nome)) {

            throw new IllegalStateException(
                "Já existe uma instituição de ensino com este nome."
            );
        }

        mapperEnsino.atualizar(dados, ensino);
        ensino.setNome(nome);

        return repositoryEnsino.save(ensino);
    }

    @Transactional
    public void deletar(Long id) {

        Ensino ensino = buscarPorId(id);

        if (repositoryAluno.existsByEnsinoId(id)) {
            throw new IllegalStateException(
                "Não é possível excluir uma instituição com alunos vinculados."
            );
        }

        repositoryEnsino.delete(ensino);
    }
}
