
package com.estudos.estagio.dto.dtoContratoEstagio;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosCadastroContratoEstagio(

    @NotNull(message = "Selecione um aluno")
    Long idAluno,

    @NotNull(message = "Selecione uma empresa")
    Long idEmpresa,

    @NotNull(message = "Informe a data de início")
    LocalDate dataInicio,

    @NotNull(message = "Informe a data de término")
    LocalDate dataFim,

    @NotNull(message = "Informe a carga horária")
    @Positive(message = "A carga horária deve ser positiva")
    Integer cargaHoraria,

    @NotBlank(message = "Informe o tipo de carga horária")
    String tipoCargaHoraria,

    @NotBlank(message = "Informe a área do estágio")
    String areaEstagio,

    @NotBlank(message = "Informe as atividades principais")
    String atividadesPrincipais,

    @NotBlank(message = "Informe o supervisor responsável")
    String supervisorResponsavel,

    @NotBlank(message = "Informe o cargo do supervisor")
    String cargoSupervisor

) {
    public static DadosCadastroContratoEstagio vazio() {
        return new DadosCadastroContratoEstagio(
            null, null, null, null, null,
            null, null, null, null, null
        );
    }
}
