
package com.estudos.estagio.mapper;

import org.springframework.stereotype.Component;

import com.estudos.estagio.dto.dtoAluno.DadosAtualizacaoAluno;
import com.estudos.estagio.dto.dtoAluno.DadosCadastroAluno;
import com.estudos.estagio.dto.dtoAluno.DadosListagemAluno;
import com.estudos.estagio.model.Aluno;

@Component
public class MapperAluno {

    public Aluno toEntity(DadosCadastroAluno dados) {

        Aluno aluno = new Aluno();

        aluno.setNomeCompleto(dados.nomeCompleto());
        aluno.setTelefone(dados.telefone());
        aluno.setEmail(dados.email());
        aluno.setCurso(dados.curso());
        aluno.setSemestre(dados.semestre());
        aluno.setPeriodo(dados.periodo());

        return aluno;
    }

    public void atualizar(
            DadosAtualizacaoAluno dados,
            Aluno aluno) {

        aluno.setNomeCompleto(dados.nomeCompleto());
        aluno.setTelefone(dados.telefone());
        aluno.setEmail(dados.email());
        aluno.setCurso(dados.curso());
        aluno.setSemestre(dados.semestre());
        aluno.setPeriodo(dados.periodo());
    }

    public DadosListagemAluno toListagem(Aluno aluno) {

        return new DadosListagemAluno(aluno);
    }

    public DadosAtualizacaoAluno toAtualizacao(Aluno aluno) {

        return new DadosAtualizacaoAluno(
                aluno.getId(),
                aluno.getNomeCompleto(),
                aluno.getTelefone(),
                aluno.getEmail(),
                aluno.getCurso(),
                aluno.getSemestre(),
                aluno.getPeriodo(),
                aluno.getEnsino() != null
                        ? aluno.getEnsino().getId()
                        : null);
    }
}
