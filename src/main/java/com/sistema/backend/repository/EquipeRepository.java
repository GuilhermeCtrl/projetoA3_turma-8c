package com.sistema.backend.repository;

import com.sistema.backend.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    Optional<Equipe> findByNome(String nome);
}

