
package com.estudos.estagio.dto.dtoEnsino;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoEnsino(

    @NotNull(message = "Informe o ID da instituição de ensino")
    Long id,

    @NotBlank(message = "Informe o nome da instituição de ensino")
    String nome

) {
}
