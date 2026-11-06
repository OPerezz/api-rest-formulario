
package com.estudos.estagio.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ensino")
@Getter
@Setter
@NoArgsConstructor
public class Ensino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Ensino(String nome) {
        this.nome = nome;
    }
}
