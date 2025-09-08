package com.locadora.service;

import com.locadora.model.Genero;
import com.locadora.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneroService {
    
    @Autowired
    private GeneroRepository generoRepository;
    
    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }
    
    public Optional<Genero> buscarPorId(Long id) {
        return generoRepository.findById(id);
    }
    
    public List<Genero> buscarPorNome(String nome) {
        return generoRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    public Genero salvar(Genero genero) {
        // Verificar se já existe um gênero com o mesmo nome
        if (generoRepository.existsByNomeIgnoreCase(genero.getNome())) {
            throw new RuntimeException("Já existe um gênero com este nome: " + genero.getNome());
        }
        return generoRepository.save(genero);
    }
    
    public Genero atualizar(Long id, Genero generoAtualizado) {
        Genero generoExistente = generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gênero não encontrado com ID: " + id));
        
        // Verificar se o novo nome já existe em outro gênero
        if (!generoExistente.getNome().equalsIgnoreCase(generoAtualizado.getNome()) &&
            generoRepository.existsByNomeIgnoreCase(generoAtualizado.getNome())) {
            throw new RuntimeException("Já existe um gênero com este nome: " + generoAtualizado.getNome());
        }
        
        generoExistente.setNome(generoAtualizado.getNome());
        return generoRepository.save(generoExistente);
    }
    
    public void deletar(Long id) {
        if (!generoRepository.existsById(id)) {
            throw new RuntimeException("Gênero não encontrado com ID: " + id);
        }
        generoRepository.deleteById(id);
    }
    
    public boolean existePorNome(String nome) {
        return generoRepository.existsByNomeIgnoreCase(nome);
    }
}
