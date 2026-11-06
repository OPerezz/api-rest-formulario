
package com.estudos.estagio.repository;

import java.util.List;

import com.estudos.estagio.model.Ensino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEnsino extends JpaRepository<Ensino, Long> {

    List<Ensino> findByNomeContainingIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
