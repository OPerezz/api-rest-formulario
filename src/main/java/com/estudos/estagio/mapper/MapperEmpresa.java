
package com.estudos.estagio.mapper;

import org.springframework.stereotype.Component;

import com.estudos.estagio.dto.dtoEmpresa.DadosAtualizacaoEmpresa;
import com.estudos.estagio.dto.dtoEmpresa.DadosCadastroEmpresa;
import com.estudos.estagio.dto.dtoEmpresa.DadosListagemEmpresa;
import com.estudos.estagio.model.Empresa;

@Component
public class MapperEmpresa {

  
    public Empresa toEntity(DadosCadastroEmpresa dados) {

        Empresa empresa = new Empresa();

        empresa.setNomeEmpresa(dados.nomeEmpresa());
        empresa.setAreaAtuacao(dados.areaAtuacao());
        empresa.setCidade(dados.cidade());
        empresa.setCep(dados.cep());
        empresa.setEmail(dados.email());
        empresa.setSite(dados.site());
        empresa.setTel(dados.tel());
        empresa.setNomeContato(dados.nomeContato());
        empresa.setCargoContato(dados.cargoContato());
        empresa.setDepartamento(dados.departamento());
        empresa.setLinked(dados.linked());

        return empresa;
    }

    public void atualizar(DadosAtualizacaoEmpresa dados, Empresa empresa) {

        empresa.setNomeEmpresa(dados.nomeEmpresa());
        empresa.setAreaAtuacao(dados.areaAtuacao());
        empresa.setCidade(dados.cidade());
        empresa.setCep(dados.cep());
        empresa.setEmail(dados.email());
        empresa.setSite(dados.site());
        empresa.setTel(dados.tel());
        empresa.setNomeContato(dados.nomeContato());
        empresa.setCargoContato(dados.cargoContato());
        empresa.setDepartamento(dados.departamento());
        empresa.setLinked(dados.linked());
    }

    public DadosListagemEmpresa toListagem(Empresa empresa) {

        return new DadosListagemEmpresa(empresa);
    }

    public DadosAtualizacaoEmpresa toAtualizacao(Empresa empresa) {

        return new DadosAtualizacaoEmpresa(
            empresa.getId(),
            empresa.getNomeEmpresa(),
            empresa.getAreaAtuacao(),
            empresa.getCidade(),
            empresa.getCep(),
            empresa.getEmail(),
            empresa.getSite(),
            empresa.getTel(),
            empresa.getNomeContato(),
            empresa.getCargoContato(),
            empresa.getDepartamento(),
            empresa.getLinked()
        );
    }
}
