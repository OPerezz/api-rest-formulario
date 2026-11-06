
package com.estudos.estagio.repository;

import java.util.List;

import com.estudos.estagio.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEmpresa extends JpaRepository<Empresa, Long> {

    List<Empresa> findByNomeEmpresaContainingIgnoreCase(String nome);


    List<Empresa> findByCidadeIgnoreCase(String cidade);


    boolean existsByEmail(String email);
}
