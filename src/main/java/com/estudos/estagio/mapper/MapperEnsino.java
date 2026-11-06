
package com.estudos.estagio.mapper;

import org.springframework.stereotype.Component;

import com.estudos.estagio.dto.dtoEnsino.DadosAtualizacaoEnsino;
import com.estudos.estagio.dto.dtoEnsino.DadosCadastroEnsino;
import com.estudos.estagio.dto.dtoEnsino.DadosListagemEnsino;
import com.estudos.estagio.model.Ensino;

@Component
public class MapperEnsino {

   
    public Ensino toEntity(DadosCadastroEnsino dados) {

        Ensino ensino = new Ensino();

        ensino.setNome(dados.nome());

        return ensino;
    }


    public void atualizar(DadosAtualizacaoEnsino dados, Ensino ensino) {

        ensino.setNome(dados.nome());
    }

    public DadosListagemEnsino toListagem(Ensino ensino) {

        return new DadosListagemEnsino(ensino);
    }


    public DadosAtualizacaoEnsino toAtualizacao(Ensino ensino) {

        return new DadosAtualizacaoEnsino(
            ensino.getId(),
            ensino.getNome()
        );
    }
}
