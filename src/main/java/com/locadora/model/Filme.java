package com.locadora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "filmes")
public class Filme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Título do filme é obrigatório")
    @Size(min = 1, max = 200, message = "Título deve ter entre 1 e 200 caracteres")
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @NotNull(message = "Ano do filme é obrigatório")
    @Positive(message = "Ano deve ser positivo")
    @Column(nullable = false)
    private Integer ano;
    
    @NotBlank(message = "Classificação etária é obrigatória")
    @Size(min = 1, max = 10, message = "Classificação etária deve ter entre 1 e 10 caracteres")
    @Column(nullable = false, length = 10)
    private String classificacaoEtaria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero_id", nullable = false)
    @NotNull(message = "Gênero é obrigatório")
    private Genero genero;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produtora_id", nullable = false)
    @NotNull(message = "Produtora é obrigatória")
    private Produtora produtora;
    
    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Locacao> locacoes;
    
    // Construtores
    public Filme() {}
    
    public Filme(String titulo, Integer ano, String classificacaoEtaria, Genero genero, Produtora produtora) {
        this.titulo = titulo;
        this.ano = ano;
        this.classificacaoEtaria = classificacaoEtaria;
        this.genero = genero;
        this.produtora = produtora;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Integer getAno() {
        return ano;
    }
    
    public void setAno(Integer ano) {
        this.ano = ano;
    }
    
    public String getClassificacaoEtaria() {
        return classificacaoEtaria;
    }
    
    public void setClassificacaoEtaria(String classificacaoEtaria) {
        this.classificacaoEtaria = classificacaoEtaria;
    }
    
    public Genero getGenero() {
        return genero;
    }
    
    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    public Produtora getProdutora() {
        return produtora;
    }
    
    public void setProdutora(Produtora produtora) {
        this.produtora = produtora;
    }
    
    public List<Locacao> getLocacoes() {
        return locacoes;
    }
    
    public void setLocacoes(List<Locacao> locacoes) {
        this.locacoes = locacoes;
    }
    
    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", ano=" + ano +
                ", classificacaoEtaria='" + classificacaoEtaria + '\'' +
                ", genero=" + genero +
                ", produtora=" + produtora +
                '}';
    }
}
