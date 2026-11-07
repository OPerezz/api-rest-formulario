
package com.estudos.estagio.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import com.estudos.estagio.model.Aluno;
import com.estudos.estagio.model.Empresa;
import com.estudos.estagio.model.Ensino;
import com.estudos.estagio.model.ContratoEstagio;

import com.estudos.estagio.repository.RepositoryAluno;
import com.estudos.estagio.repository.RepositoryEmpresa;
import com.estudos.estagio.repository.RepositoryEnsino;
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
    private final RepositoryEnsino repositoryEnsino;
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
    public Long enviarFormulario(Map<String, String> campos) {

        String nomeEmpresa = obrigatorio(campos, "nomeEmpresa");
        String emailEmpresa = obrigatorio(campos, "emailEmpresa");
        String nomeEnsino = obrigatorio(campos, "nomeEnsino");
        String nomeAluno = obrigatorio(campos, "nomeCompleto");
        String telefoneAluno = obrigatorio(campos, "telefoneAluno");
        String emailAluno = obrigatorio(campos, "emailAluno");
        String curso = obrigatorio(campos, "curso");
        Integer semestre = numeroPositivo(campos, "semestre");
        String periodo = obrigatorio(campos, "periodo");

        LocalDate dataInicio = dataObrigatoria(campos, "dataInicio");
        LocalDate dataFim = dataObrigatoria(campos, "dataFim");
        Integer cargaHoraria = numeroPositivo(campos, "cargaHoraria");
        String tipoCargaHoraria = obrigatorio(campos, "tipoCargaHoraria");
        String areaEstagio = obrigatorio(campos, "areaEstagio");
        String atividadesPrincipais = obrigatorio(campos, "atividadesPrincipais");
        String supervisorResponsavel = obrigatorio(campos, "supervisorResponsavel");
        String cargoSupervisor = obrigatorio(campos, "cargoSupervisor");

        obrigatorio(campos, "localAssinatura");
        dataObrigatoria(campos, "dataAssinatura");
        obrigatorio(campos, "assinaturaRepresentanteEmpresa");
        obrigatorio(campos, "assinaturaEstagiario");
        obrigatorio(campos, "assinaturaInstituicao");

        if (!"true".equalsIgnoreCase(texto(campos, "aceiteDeclaracao"))) {
            throw new IllegalArgumentException(
                    "É necessário confirmar a declaração.");
        }

        validarPeriodo(dataInicio, dataFim);

        if (!"Diária".equals(tipoCargaHoraria)
                && !"Semanal".equals(tipoCargaHoraria)) {
            throw new IllegalArgumentException(
                    "O tipo de carga horária deve ser Diária ou Semanal.");
        }

        if (repositoryAluno.existsByEmail(emailAluno)) {
            throw new IllegalArgumentException(
                    "Já existe um aluno com esse e-mail.");
        }

        if (repositoryempresa.existsByEmail(emailEmpresa)) {
            throw new IllegalArgumentException(
                    "Já existe uma empresa com esse e-mail.");
        }

        Ensino ensino = repositoryEnsino.findAll()
                .stream()
                .filter(item ->
                        item.getNome() != null
                        && item.getNome().trim().equalsIgnoreCase(nomeEnsino))
                .findFirst()
                .orElse(null);

        if (ensino == null) {
            ensino = new Ensino();
            ensino.setNome(nomeEnsino);
            ensino = repositoryEnsino.save(ensino);
        }

        Empresa empresa = new Empresa();
        empresa.setNomeEmpresa(nomeEmpresa);
        empresa.setAreaAtuacao(texto(campos, "areaAtuacao"));
        empresa.setCidade(texto(campos, "cidade"));
        empresa.setCep(texto(campos, "cep"));
        empresa.setEmail(emailEmpresa);
        empresa.setSite(texto(campos, "site"));
        empresa.setTel(texto(campos, "telEmpresa"));
        empresa.setNomeContato(texto(campos, "nomeContato"));
        empresa.setCargoContato(texto(campos, "cargoContato"));
        empresa.setDepartamento(texto(campos, "departamento"));
        empresa.setLinked(texto(campos, "linked"));

        empresa = repositoryempresa.save(empresa);

        Aluno aluno = new Aluno();
        aluno.setNomeCompleto(nomeAluno);
        aluno.setTelefone(telefoneAluno);
        aluno.setEmail(emailAluno);
        aluno.setCurso(curso);
        aluno.setSemestre(semestre);
        aluno.setPeriodo(periodo);
        aluno.setEnsino(ensino);

        aluno = repositoryAluno.save(aluno);

        DadosCadastroContratoEstagio dadosContrato =
                new DadosCadastroContratoEstagio(
                        aluno.getId(),
                        empresa.getId(),
                        dataInicio,
                        dataFim,
                        cargaHoraria,
                        tipoCargaHoraria,
                        areaEstagio,
                        atividadesPrincipais,
                        supervisorResponsavel,
                        cargoSupervisor
                );

        ContratoEstagio contrato = criar(dadosContrato);

        repositorycontrato.flush();

        return contrato.getId();
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

    private String texto(Map<String, String> campos, String nome) {

        String valor = campos.get(nome);

        return valor == null ? "" : valor.trim();
    }

    private String obrigatorio(Map<String, String> campos, String nome) {

        String valor = texto(campos, nome);

        if (valor.isBlank()) {
            throw new IllegalArgumentException(
                    "O campo " + nome + " é obrigatório.");
        }

        return valor;
    }

    private Integer numeroPositivo(Map<String, String> campos, String nome) {

        String valor = obrigatorio(campos, nome);

        try {
            int numero = Integer.parseInt(valor);

            if (numero <= 0) {
                throw new IllegalArgumentException(
                        "O campo " + nome + " deve ser maior que zero.");
            }

            return numero;
        } catch (NumberFormatException erro) {
            throw new IllegalArgumentException(
                    "O campo " + nome + " deve conter um número válido.");
        }
    }

    private LocalDate dataObrigatoria(
            Map<String, String> campos,
            String nome) {

        String valor = obrigatorio(campos, nome);

        try {
            return LocalDate.parse(valor);
        } catch (DateTimeParseException erro) {
            throw new IllegalArgumentException(
                    "A data informada no campo " + nome + " é inválida.");
        }
    }
}
