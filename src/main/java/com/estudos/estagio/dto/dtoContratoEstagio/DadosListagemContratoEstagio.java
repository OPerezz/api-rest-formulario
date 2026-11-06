
package com.estudos.estagio.dto.dtoContratoEstagio;

import java.time.LocalDate;

import com.estudos.estagio.model.ContratoEstagio;

public record DadosListagemContratoEstagio(

    Long id,
    Long idAluno,
    String nomeAluno,
    Long idEmpresa,
    String nomeEmpresa,
    LocalDate dataInicio,
    LocalDate dataFim,
    Integer cargaHoraria,
    String tipoCargaHoraria,
    String areaEstagio,
    String atividadesPrincipais,
    String supervisorResponsavel,
    String cargoSupervisor

) {
    public DadosListagemContratoEstagio(ContratoEstagio contrato) {
        this(
            contrato.getId(),
            contrato.getAluno().getId(),
            contrato.getAluno().getNomeCompleto(),
            contrato.getEmpresa().getId(),
            contrato.getEmpresa().getNomeEmpresa(),
            contrato.getDataInicio(),
            contrato.getDataFim(),
            contrato.getCargaHoraria(),
            contrato.getTipoCargaHoraria(),
            contrato.getAreaEstagio(),
            contrato.getAtividadesPrincipais(),
            contrato.getSupervisorResponsavel(),
            contrato.getCargoSupervisor()
        );
    }
}
