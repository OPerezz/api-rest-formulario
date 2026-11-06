
package com.estudos.estagio.dto.dtoEnsino;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroEnsino(

    @NotBlank(message = "Informe o nome da instituição de ensino")
    String nome

) {
    public static DadosCadastroEnsino vazio() {
        return new DadosCadastroEnsino(null);
    }
}
