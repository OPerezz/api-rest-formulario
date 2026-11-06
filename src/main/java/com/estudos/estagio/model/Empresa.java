
package com.estudos.estagio.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_empresa", nullable = false)
    private String nomeEmpresa;

    @Column(name = "area_atuacao")
    private String areaAtuacao;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "cep")
    private String cep;

    @Column(name = "email")
    private String email;

    @Column(name = "site")
    private String site;

    @Column(name = "tel")
    private String tel;

    @Column(name = "nome_contato")
    private String nomeContato;

    @Column(name = "cargo_contato")
    private String cargoContato;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "linked")
    private String linked;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Empresa(String nomeEmpresa, String areaAtuacao,
            String cidade, String cep, String email,
            String site, String tel, String nomeContato,
            String cargoContato, String departamento,
            String linked) {

        this.nomeEmpresa = nomeEmpresa;
        this.areaAtuacao = areaAtuacao;
        this.cidade = cidade;
        this.cep = cep;
        this.email = email;
        this.site = site;
        this.tel = tel;
        this.nomeContato = nomeContato;
        this.cargoContato = cargoContato;
        this.departamento = departamento;
        this.linked = linked;
    }
}
