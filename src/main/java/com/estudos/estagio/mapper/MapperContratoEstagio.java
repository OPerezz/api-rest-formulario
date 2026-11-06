
package com.estudos.estagio.mapper;

import org.springframework.stereotype.Component;

import com.estudos.estagio.dto.dtoContratoEstagio.DadosAtualizacaoContratoEstagio;
import com.estudos.estagio.dto.dtoContratoEstagio.DadosCadastroContratoEstagio;
import com.estudos.estagio.dto.dtoContratoEstagio.DadosListagemContratoEstagio;
import com.estudos.estagio.model.ContratoEstagio;

@Component
public class MapperContratoEstagio {

    public ContratoEstagio toEntity(DadosCadastroContratoEstagio dados) {

        ContratoEstagio contrato = new ContratoEstagio();

        contrato.setDataInicio(dados.dataInicio());
        contrato.setDataFim(dados.dataFim());
        contrato.setCargaHoraria(dados.cargaHoraria());
        contrato.setTipoCargaHoraria(dados.tipoCargaHoraria());
        contrato.setAreaEstagio(dados.areaEstagio());
        contrato.setAtividadesPrincipais(dados.atividadesPrincipais());
        contrato.setSupervisorResponsavel(dados.supervisorResponsavel());
        contrato.setCargoSupervisor(dados.cargoSupervisor());

        return contrato;
    }

    public void atualizar(
            DadosAtualizacaoContratoEstagio dados,
            ContratoEstagio contrato) {

        contrato.setDataInicio(dados.dataInicio());
        contrato.setDataFim(dados.dataFim());
        contrato.setCargaHoraria(dados.cargaHoraria());
        contrato.setTipoCargaHoraria(dados.tipoCargaHoraria());
        contrato.setAreaEstagio(dados.areaEstagio());
        contrato.setAtividadesPrincipais(dados.atividadesPrincipais());
        contrato.setSupervisorResponsavel(dados.supervisorResponsavel());
        contrato.setCargoSupervisor(dados.cargoSupervisor());
    }

    public DadosListagemContratoEstagio toListagem(ContratoEstagio contrato) {

        return new DadosListagemContratoEstagio(contrato);
    }


    public DadosAtualizacaoContratoEstagio toAtualizacao(ContratoEstagio contrato) {

        return new DadosAtualizacaoContratoEstagio(
            contrato.getId(),
            contrato.getAluno().getId(),
            contrato.getEmpresa().getId(),
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
