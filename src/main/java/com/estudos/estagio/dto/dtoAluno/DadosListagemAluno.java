
package com.estudos.estagio.dto.dtoAluno;

import com.estudos.estagio.model.Aluno;

public record DadosListagemAluno(
    Long id,
    String nomeCompleto,
    String telefone,
    String email,
    String curso,
    Integer semestre,
    String periodo,
    String ensino
) {

    public DadosListagemAluno(Aluno aluno) {
        this(
            aluno.getId(),
            aluno.getNomeCompleto(),
            aluno.getTelefone(),
            aluno.getEmail(),
            aluno.getCurso(),
            aluno.getSemestre(),
            aluno.getPeriodo(),
            aluno.getEnsino() != null
                ? aluno.getEnsino().getNome()
                : null
        );
    }
}
