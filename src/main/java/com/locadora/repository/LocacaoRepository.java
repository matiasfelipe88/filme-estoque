package com.locadora.repository;

import com.locadora.model.Cliente;
import com.locadora.model.Filme;
import com.locadora.model.Locacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Long> {
    
    List<Locacao> findByCliente(Cliente cliente);
    
    List<Locacao> findByFilme(Filme filme);
    
    List<Locacao> findByDataLocacao(LocalDate dataLocacao);
    
    List<Locacao> findByDataDevolucaoIsNull();
    
    List<Locacao> findByDataDevolucaoIsNotNull();
    
    @Query("SELECT l FROM Locacao l WHERE l.cliente.id = :clienteId")
    List<Locacao> findByClienteId(@Param("clienteId") Long clienteId);
    
    @Query("SELECT l FROM Locacao l WHERE l.filme.id = :filmeId")
    List<Locacao> findByFilmeId(@Param("filmeId") Long filmeId);
    
    @Query("SELECT l FROM Locacao l WHERE l.dataLocacao BETWEEN :dataInicio AND :dataFim")
    List<Locacao> findByDataLocacaoBetween(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    @Query("SELECT l FROM Locacao l WHERE l.dataDevolucao IS NULL AND l.dataLocacao < :dataLimite")
    List<Locacao> findLocacoesAtrasadas(@Param("dataLimite") LocalDate dataLimite);
}
