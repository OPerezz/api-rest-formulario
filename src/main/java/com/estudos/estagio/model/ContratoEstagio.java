
package com.estudos.estagio.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "contrato_estagio")
@Getter
@Setter
@NoArgsConstructor
public class ContratoEstagio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    @Column(name = "tipo_carga_horaria")
    private String tipoCargaHoraria;

    @Column(name = "area_estagio")
    private String areaEstagio;

    @Column(name = "atividades_principais", columnDefinition = "TEXT")
    private String atividadesPrincipais;

    @Column(name = "supervisor_responsavel")
    private String supervisorResponsavel;

    @Column(name = "cargo_supervisor")
    private String cargoSupervisor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ContratoEstagio(
            Aluno aluno,
            Empresa empresa,
            LocalDate dataInicio,
            LocalDate dataFim,
            Integer cargaHoraria,
            String tipoCargaHoraria,
            String areaEstagio,
            String atividadesPrincipais,
            String supervisorResponsavel,
            String cargoSupervisor) {

        this.aluno = aluno;
        this.empresa = empresa;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.cargaHoraria = cargaHoraria;
        this.tipoCargaHoraria = tipoCargaHoraria;
        this.areaEstagio = areaEstagio;
        this.atividadesPrincipais = atividadesPrincipais;
        this.supervisorResponsavel = supervisorResponsavel;
        this.cargoSupervisor = cargoSupervisor;
    }
}
