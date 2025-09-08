package com.locadora.repository;

import com.locadora.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
    
    List<Genero> findByNomeContainingIgnoreCase(String nome);
    
    Optional<Genero> findByNomeIgnoreCase(String nome);
    
    boolean existsByNomeIgnoreCase(String nome);
}
