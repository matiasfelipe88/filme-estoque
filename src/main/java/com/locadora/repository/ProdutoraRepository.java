package com.locadora.repository;

import com.locadora.model.Produtora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoraRepository extends JpaRepository<Produtora, Long> {
    
    List<Produtora> findByNomeContainingIgnoreCase(String nome);
    
    List<Produtora> findByPaisContainingIgnoreCase(String pais);
    
    Optional<Produtora> findByNomeIgnoreCase(String nome);
    
    boolean existsByNomeIgnoreCase(String nome);
}
