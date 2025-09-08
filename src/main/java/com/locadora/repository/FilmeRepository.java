package com.locadora.repository;

import com.locadora.model.Filme;
import com.locadora.model.Genero;
import com.locadora.model.Produtora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    
    List<Filme> findByTituloContainingIgnoreCase(String titulo);
    
    List<Filme> findByAno(Integer ano);
    
    List<Filme> findByGenero(Genero genero);
    
    List<Filme> findByProdutora(Produtora produtora);
    
    List<Filme> findByClassificacaoEtaria(String classificacaoEtaria);
    
    @Query("SELECT f FROM Filme f WHERE f.titulo LIKE %:titulo% AND f.ano = :ano")
    List<Filme> findByTituloAndAno(@Param("titulo") String titulo, @Param("ano") Integer ano);
    
    @Query("SELECT f FROM Filme f WHERE f.genero.nome LIKE %:genero%")
    List<Filme> findByGeneroNome(@Param("genero") String genero);
    
    @Query("SELECT f FROM Filme f WHERE f.produtora.nome LIKE %:produtora%")
    List<Filme> findByProdutoraNome(@Param("produtora") String produtora);
}
