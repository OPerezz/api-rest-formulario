
package com.estudos.estagio.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import com.estudos.estagio.model.Aluno;
import com.estudos.estagio.model.Empresa;
import com.estudos.estagio.model.ContratoEstagio;

import com.estudos.estagio.repository.RepositoryAluno;
import com.estudos.estagio.repository.RepositoryEmpresa;
import com.estudos.estagio.repository.RepositoryContratoEstagio;

import com.estudos.estagio.mapper.MapperContratoEstagio;

import com.estudos.estagio.dto.dtoContratoEstagio.DadosCadastroContratoEstagio;
import com.estudos.estagio.dto.dtoContratoEstagio.DadosAtualizacaoContratoEstagio;
import com.estudos.estagio.dto.dtoContratoEstagio.DadosListagemContratoEstagio;

@Service
@RequiredArgsConstructor
public class ServiceContratoEstagio {

    private final RepositoryContratoEstagio repositorycontrato;
    private final RepositoryAluno repositoryAluno;
    private final RepositoryEmpresa repositoryempresa;
    private final MapperContratoEstagio mapperContrato;

    @Transactional(readOnly = true)
    public List<DadosListagemContratoEstagio> listar() {

        return repositorycontrato.findAll()
                .stream()
                .map(mapperContrato::toListagem)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DadosListagemContratoEstagio> listarPorAluno(Long idAluno) {

        return repositorycontrato.findByAlunoId(idAluno)
                .stream()
                .map(mapperContrato::toListagem)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DadosListagemContratoEstagio> listarPorEmpresa(Long idEmpresa) {

        return repositorycontrato.findByEmpresaId(idEmpresa)
                .stream()
                .map(mapperContrato::toListagem)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContratoEstagio buscarPorId(Long id) {

        return repositorycontrato.findById(id)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Contrato não encontrado com ID: " + id
                    )
                );
    }

    @Transactional(readOnly = true)
    public DadosAtualizacaoContratoEstagio buscarParaEdicao(Long id) {

        ContratoEstagio contrato = buscarPorId(id);

        return mapperContrato.toAtualizacao(contrato);
    }

    @Transactional
    public ContratoEstagio criar(DadosCadastroContratoEstagio dados) {

        validarPeriodo(dados.dataInicio(), dados.dataFim());

        Aluno aluno = resolverAluno(dados.idAluno());
        Empresa empresa = resolverEmpresa(dados.idEmpresa());

        ContratoEstagio contrato = mapperContrato.toEntity(dados);

        contrato.setAluno(aluno);
        contrato.setEmpresa(empresa);

        return repositorycontrato.save(contrato);
    }

    @Transactional
    public ContratoEstagio atualizar(DadosAtualizacaoContratoEstagio dados) {

        ContratoEstagio contrato = buscarPorId(dados.id());

        validarPeriodo(dados.dataInicio(), dados.dataFim());

        Aluno aluno = resolverAluno(dados.idAluno());
        Empresa empresa = resolverEmpresa(dados.idEmpresa());

        mapperContrato.atualizar(dados, contrato);

        contrato.setAluno(aluno);
        contrato.setEmpresa(empresa);

        return repositorycontrato.save(contrato);
    }

    @Transactional
    public void deletar(Long id) {

        ContratoEstagio contrato = buscarPorId(id);

        repositorycontrato.delete(contrato);
    }

    private Aluno resolverAluno(Long idAluno) {

        return repositoryAluno.findById(idAluno)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Aluno não encontrado com ID: " + idAluno
                    )
                );
    }

    private Empresa resolverEmpresa(Long idEmpresa) {

        return repositoryempresa.findById(idEmpresa)
                .orElseThrow(() ->
                    new EntityNotFoundException(
                        "Empresa não encontrada com ID: " + idEmpresa
                    )
                );
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {

        if (inicio == null || fim == null) {
            throw new IllegalArgumentException(
                "As datas de início e término são obrigatórias."
            );
        }

        if (!fim.isAfter(inicio)) {
            throw new IllegalArgumentException(
                "A data de término deve ser posterior à data de início."
            );
        }
    }
}
