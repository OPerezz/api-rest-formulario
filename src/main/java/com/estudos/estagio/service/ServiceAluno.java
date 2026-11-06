
package com.estudos.estagio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estudos.estagio.dto.dtoAluno.DadosAtualizacaoAluno;
import com.estudos.estagio.dto.dtoAluno.DadosCadastroAluno;
import com.estudos.estagio.dto.dtoAluno.DadosListagemAluno;
import com.estudos.estagio.mapper.MapperAluno;
import com.estudos.estagio.model.Aluno;
import com.estudos.estagio.model.Ensino;
import com.estudos.estagio.repository.RepositoryAluno;
import com.estudos.estagio.repository.RepositoryContratoEstagio;
import com.estudos.estagio.repository.RepositoryEnsino;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceAluno {

    private final RepositoryAluno alunoRepository;
    private final RepositoryEnsino ensinoRepository;
    private final RepositoryContratoEstagio contratoRepository;
    private final MapperAluno mapperAluno;


    @Transactional(readOnly = true)
    public List<DadosListagemAluno> listar(String nome) {

        List<Aluno> alunos;

        if (nome == null || nome.isBlank()) {
            alunos = alunoRepository.findAll();
        } else {
            alunos = alunoRepository
                    .findByNomeCompletoContainingIgnoreCase(nome.trim());
        }

        return alunos.stream()
                .map(mapperAluno::toListagem)
                .toList();
    }


    @Transactional(readOnly = true)
    public Aluno buscarPorId(Long id) {

        return alunoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Aluno não encontrado com ID: " + id));
    }


    @Transactional(readOnly = true)
    public DadosAtualizacaoAluno buscarParaEdicao(Long id) {

        Aluno aluno = buscarPorId(id);

        return mapperAluno.toAtualizacao(aluno);
    }


    @Transactional
    public Aluno criar(DadosCadastroAluno dados) {

        if (alunoRepository.existsByEmail(dados.email())) {
            throw new IllegalStateException(
                    "Já existe um aluno com este e-mail.");
        }

        Ensino ensino = resolverEnsino(dados.idEnsino());

        Aluno aluno = mapperAluno.toEntity(dados);

        aluno.setEnsino(ensino);

        return alunoRepository.save(aluno);
    }


    @Transactional
    public Aluno atualizar(DadosAtualizacaoAluno dados) {

        Aluno aluno = buscarPorId(dados.id());

        if (!aluno.getEmail().equalsIgnoreCase(dados.email())
                && alunoRepository.existsByEmail(dados.email())) {

            throw new IllegalStateException(
                    "Já existe um aluno com este e-mail.");
        }

        mapperAluno.atualizar(dados, aluno);

        Ensino ensino = resolverEnsino(dados.idEnsino());

        aluno.setEnsino(ensino);

        return alunoRepository.save(aluno);
    }


    @Transactional
    public void deletar(Long id) {

        Aluno aluno = buscarPorId(id);

        if (contratoRepository.existsByAlunoId(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir um aluno com contratos vinculados.");
        }

        alunoRepository.delete(aluno);
    }

    private Ensino resolverEnsino(Long idEnsino) {

        return ensinoRepository.findById(idEnsino)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Instituição de ensino não encontrada: " + idEnsino));
    }
}
