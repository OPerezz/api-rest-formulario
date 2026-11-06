
package com.estudos.estagio.dto.dtoAluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoAluno(

    @NotNull(message = "Informe o ID do aluno")
    Long id,

    @NotBlank(message = "Informe o nome completo")
    String nomeCompleto,

    @NotBlank(message = "Informe o telefone")
    String telefone,

    @NotBlank(message = "Informe o e-mail")
    @Email(message = "E-mail inválido")
    String email,

    @NotBlank(message = "Informe o curso")
    String curso,

    @NotNull(message = "Informe o semestre")
    @Min(value = 1, message = "Semestre inválido")
    Integer semestre,

    @NotBlank(message = "Informe o período")
    String periodo,

    @NotNull(message = "Selecione uma escola")
    Long idEnsino

) {
}
