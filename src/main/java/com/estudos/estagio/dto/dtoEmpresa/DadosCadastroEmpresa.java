
package com.estudos.estagio.dto.dtoEmpresa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosCadastroEmpresa(

    @NotBlank(message = "Informe o nome da empresa")
    String nomeEmpresa,

    String areaAtuacao,

    String cidade,

    String cep,

    @Email(message = "E-mail inválido")
    String email,

    String site,

    String tel,

    String nomeContato,

    String cargoContato,

    String departamento,

    String linked

) {
    public static DadosCadastroEmpresa vazio() {
        return new DadosCadastroEmpresa(
            null, null, null, null, null,
            null, null, null, null, null, null
        );
    }
}
