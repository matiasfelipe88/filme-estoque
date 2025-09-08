package com.locadora.service;

import com.locadora.model.Filme;
import com.locadora.model.Genero;
import com.locadora.model.Produtora;
import com.locadora.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {
    
    @Autowired
    private FilmeRepository filmeRepository;
    
    @Autowired
    private GeneroService generoService;
    
    @Autowired
    private ProdutoraService produtoraService;
    
    public List<Filme> listarTodos() {
        return filmeRepository.findAll();
    }
    
    public Optional<Filme> buscarPorId(Long id) {
        return filmeRepository.findById(id);
    }
    
    public List<Filme> buscarPorTitulo(String titulo) {
        return filmeRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Filme> buscarPorAno(Integer ano) {
        return filmeRepository.findByAno(ano);
    }
    
    public List<Filme> buscarPorGenero(Long generoId) {
        Genero genero = generoService.buscarPorId(generoId)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com ID: " + generoId));
        return filmeRepository.findByGenero(genero);
    }
    
    public List<Filme> buscarPorProdutora(Long produtoraId) {
        Produtora produtora = produtoraService.buscarPorId(produtoraId)
                .orElseThrow(() -> new RuntimeException("Produtora não encontrada com ID: " + produtoraId));
        return filmeRepository.findByProdutora(produtora);
    }
    
    public List<Filme> buscarPorClassificacaoEtaria(String classificacaoEtaria) {
        return filmeRepository.findByClassificacaoEtaria(classificacaoEtaria);
    }
    
    public List<Filme> buscarPorTituloEAno(String titulo, Integer ano) {
        return filmeRepository.findByTituloAndAno(titulo, ano);
    }
    
    public List<Filme> buscarPorGeneroNome(String genero) {
        return filmeRepository.findByGeneroNome(genero);
    }
    
    public List<Filme> buscarPorProdutoraNome(String produtora) {
        return filmeRepository.findByProdutoraNome(produtora);
    }
    
    public Filme salvar(Filme filme) {
        // Verificar se o gênero existe
        if (filme.getGenero() != null && filme.getGenero().getId() != null) {
            Genero genero = generoService.buscarPorId(filme.getGenero().getId())
                    .orElseThrow(() -> new RuntimeException("Gênero não encontrado com ID: " + filme.getGenero().getId()));
            filme.setGenero(genero);
        }
        
        // Verificar se a produtora existe
        if (filme.getProdutora() != null && filme.getProdutora().getId() != null) {
            Produtora produtora = produtoraService.buscarPorId(filme.getProdutora().getId())
                    .orElseThrow(() -> new RuntimeException("Produtora não encontrada com ID: " + filme.getProdutora().getId()));
            filme.setProdutora(produtora);
        }
        
        return filmeRepository.save(filme);
    }
    
    public Filme atualizar(Long id, Filme filmeAtualizado) {
        Filme filmeExistente = filmeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com ID: " + id));
        
        // Verificar se o gênero existe
        if (filmeAtualizado.getGenero() != null && filmeAtualizado.getGenero().getId() != null) {
            Genero genero = generoService.buscarPorId(filmeAtualizado.getGenero().getId())
                    .orElseThrow(() -> new RuntimeException("Gênero não encontrado com ID: " + filmeAtualizado.getGenero().getId()));
            filmeExistente.setGenero(genero);
        }
        
        // Verificar se a produtora existe
        if (filmeAtualizado.getProdutora() != null && filmeAtualizado.getProdutora().getId() != null) {
            Produtora produtora = produtoraService.buscarPorId(filmeAtualizado.getProdutora().getId())
                    .orElseThrow(() -> new RuntimeException("Produtora não encontrada com ID: " + filmeAtualizado.getProdutora().getId()));
            filmeExistente.setProdutora(produtora);
        }
        
        filmeExistente.setTitulo(filmeAtualizado.getTitulo());
        filmeExistente.setAno(filmeAtualizado.getAno());
        filmeExistente.setClassificacaoEtaria(filmeAtualizado.getClassificacaoEtaria());
        
        return filmeRepository.save(filmeExistente);
    }
    
    public void deletar(Long id) {
        if (!filmeRepository.existsById(id)) {
            throw new RuntimeException("Filme não encontrado com ID: " + id);
        }
        filmeRepository.deleteById(id);
    }
}
