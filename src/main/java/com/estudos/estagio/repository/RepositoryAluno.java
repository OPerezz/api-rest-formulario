package com.estudos.estagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudos.estagio.model.Aluno;

@Repository
public interface RepositoryAluno extends JpaRepository<Aluno, Long> {

    List<Aluno> findByNomeCompletoContainingIgnoreCase(String nome);

    boolean existsByEmail(String email);

    boolean existsByEnsinoId(Long ensinoId);

}