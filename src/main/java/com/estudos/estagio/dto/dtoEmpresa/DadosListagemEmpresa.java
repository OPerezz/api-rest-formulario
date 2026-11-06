
package com.estudos.estagio.dto.dtoEmpresa;

import com.estudos.estagio.model.Empresa;

public record DadosListagemEmpresa(

    Long id,
    String nomeEmpresa,
    String areaAtuacao,
    String cidade,
    String cep,
    String email,
    String site,
    String tel,
    String nomeContato,
    String cargoContato,
    String departamento,
    String linked

) {
    public DadosListagemEmpresa(Empresa empresa) {
        this(
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
