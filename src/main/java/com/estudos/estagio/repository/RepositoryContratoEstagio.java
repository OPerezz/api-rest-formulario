
package com.estudos.estagio.repository;

import java.util.List;

import com.estudos.estagio.model.ContratoEstagio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryContratoEstagio extends JpaRepository<ContratoEstagio, Long> {

    List<ContratoEstagio> findByAlunoId(Long alunoId);


    List<ContratoEstagio> findByEmpresaId(Long empresaId);

    boolean existsByAlunoId(Long alunoId);

    boolean existsByEmpresaId(Long empresaId);
}
