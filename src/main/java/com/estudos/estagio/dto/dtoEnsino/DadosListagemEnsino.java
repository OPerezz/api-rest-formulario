
package com.estudos.estagio.dto.dtoEnsino;

import com.estudos.estagio.model.Ensino;

public record DadosListagemEnsino(

    Long id,
    String nome

) {
    public DadosListagemEnsino(Ensino ensino) {
        this(
            ensino.getId(),
            ensino.getNome()
        );
    }
}
