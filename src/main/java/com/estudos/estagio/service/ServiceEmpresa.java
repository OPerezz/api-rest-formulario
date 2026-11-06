
package com.estudos.estagio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import com.estudos.estagio.model.Empresa;

import com.estudos.estagio.repository.RepositoryEmpresa;
import com.estudos.estagio.repository.RepositoryContratoEstagio;

import com.estudos.estagio.mapper.MapperEmpresa;

import com.estudos.estagio.dto.dtoEmpresa.DadosCadastroEmpresa;
import com.estudos.estagio.dto.dtoEmpresa.DadosAtualizacaoEmpresa;
import com.estudos.estagio.dto.dtoEmpresa.DadosListagemEmpresa;

@Service
@RequiredArgsConstructor
public class ServiceEmpresa {

    private final RepositoryEmpresa repositoryEmpresa;
    private final RepositoryContratoEstagio repositoryContrato;
    private final MapperEmpresa mapperEmpresa;

    @Transactional(readOnly = true)
    public List<DadosListagemEmpresa> listar(String nome) {

        List<Empresa> empresas;

        if (nome == null || nome.isBlank()) {
            empresas = repositoryEmpresa.findAll();
        } else {
            empresas = repositoryEmpresa
                    .findByNomeEmpresaContainingIgnoreCase(nome.trim());
        }

        return empresas.stream()
                .map(mapperEmpresa::toListagem)
                .toList();
    }

    @Transactional(readOnly = true)
    public Empresa buscarPorId(Long id) {

        return repositoryEmpresa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Empresa não encontrada com ID: " + id));
    }

    @Transactional(readOnly = true)
    public DadosAtualizacaoEmpresa buscarParaEdicao(Long id) {

        Empresa empresa = buscarPorId(id);

        return mapperEmpresa.toAtualizacao(empresa);
    }

    @Transactional
    public Empresa criar(DadosCadastroEmpresa dados) {

        String email = dados.email();

        if (email != null && !email.isBlank()
                && repositoryEmpresa.existsByEmail(email)) {

            throw new IllegalStateException(
                    "Já existe uma empresa com este e-mail.");
        }

        Empresa empresa = mapperEmpresa.toEntity(dados);

        return repositoryEmpresa.save(empresa);
    }

    @Transactional
    public Empresa atualizar(DadosAtualizacaoEmpresa dados) {

        Empresa empresa = buscarPorId(dados.id());

        String emailNovo = dados.email();
        String emailAtual = empresa.getEmail();

        if (emailNovo != null && !emailNovo.isBlank()
                && (emailAtual == null
                        || !emailAtual.equalsIgnoreCase(emailNovo))
                && repositoryEmpresa.existsByEmail(emailNovo)) {

            throw new IllegalStateException(
                    "Já existe uma empresa com este e-mail.");
        }

        mapperEmpresa.atualizar(dados, empresa);

        return repositoryEmpresa.save(empresa);
    }

    @Transactional
    public void deletar(Long id) {

        Empresa empresa = buscarPorId(id);

        if (repositoryContrato.existsByEmpresaId(id)) {
            throw new IllegalStateException(
                    "Não é possível excluir uma empresa com contratos vinculados.");
        }

        repositoryEmpresa.delete(empresa);
    }
}
