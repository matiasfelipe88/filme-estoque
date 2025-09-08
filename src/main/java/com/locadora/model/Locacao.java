package com.locadora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "locacoes")
public class Locacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    @NotNull(message = "Cliente é obrigatório")
    private Cliente cliente;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    @NotNull(message = "Filme é obrigatório")
    private Filme filme;
    
    @NotNull(message = "Data de locação é obrigatória")
    @Column(nullable = false)
    private LocalDate dataLocacao;
    
    @Column
    private LocalDate dataDevolucao;
    
    // Construtores
    public Locacao() {}
    
    public Locacao(Cliente cliente, Filme filme, LocalDate dataLocacao) {
        this.cliente = cliente;
        this.filme = filme;
        this.dataLocacao = dataLocacao;
    }
    
    public Locacao(Cliente cliente, Filme filme, LocalDate dataLocacao, LocalDate dataDevolucao) {
        this.cliente = cliente;
        this.filme = filme;
        this.dataLocacao = dataLocacao;
        this.dataDevolucao = dataDevolucao;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Filme getFilme() {
        return filme;
    }
    
    public void setFilme(Filme filme) {
        this.filme = filme;
    }
    
    public LocalDate getDataLocacao() {
        return dataLocacao;
    }
    
    public void setDataLocacao(LocalDate dataLocacao) {
        this.dataLocacao = dataLocacao;
    }
    
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    
    @Override
    public String toString() {
        return "Locacao{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", filme=" + filme +
                ", dataLocacao=" + dataLocacao +
                ", dataDevolucao=" + dataDevolucao +
                '}';
    }
}
