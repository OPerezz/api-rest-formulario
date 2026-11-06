
package com.estudos.estagio.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo", nullable = false)
    private String nomeCompleto;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "curso")
    private String curso;

    @Column(name = "semestre")
    private Integer semestre;

    @Column(name = "periodo")
    private String periodo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escola_id")
    private Ensino ensino;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Aluno(String nomeCompleto, String telefone,
            String email, String curso, Integer semestre,
            String periodo, Ensino ensino) {

        this.nomeCompleto = nomeCompleto;
        this.telefone = telefone;
        this.email = email;
        this.curso = curso;
        this.semestre = semestre;
        this.periodo = periodo;
        this.ensino = ensino;
    }
}
