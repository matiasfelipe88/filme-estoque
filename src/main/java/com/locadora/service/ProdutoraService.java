package com.locadora.service;

import com.locadora.model.Produtora;
import com.locadora.repository.ProdutoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoraService {
    
    @Autowired
    private ProdutoraRepository produtoraRepository;
    
    public List<Produtora> listarTodos() {
        return produtoraRepository.findAll();
    }
    
    public Optional<Produtora> buscarPorId(Long id) {
        return produtoraRepository.findById(id);
    }
    
    public List<Produtora> buscarPorNome(String nome) {
        return produtoraRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    public List<Produtora> buscarPorPais(String pais) {
        return produtoraRepository.findByPaisContainingIgnoreCase(pais);
    }
    
    public Produtora salvar(Produtora produtora) {
        // Verificar se já existe uma produtora com o mesmo nome
        if (produtoraRepository.existsByNomeIgnoreCase(produtora.getNome())) {
            throw new RuntimeException("Já existe uma produtora com este nome: " + produtora.getNome());
        }
        return produtoraRepository.save(produtora);
    }
    
    public Produtora atualizar(Long id, Produtora produtoraAtualizada) {
        Produtora produtoraExistente = produtoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produtora não encontrada com ID: " + id));
        
        // Verificar se o novo nome já existe em outra produtora
        if (!produtoraExistente.getNome().equalsIgnoreCase(produtoraAtualizada.getNome()) &&
            produtoraRepository.existsByNomeIgnoreCase(produtoraAtualizada.getNome())) {
            throw new RuntimeException("Já existe uma produtora com este nome: " + produtoraAtualizada.getNome());
        }
        
        produtoraExistente.setNome(produtoraAtualizada.getNome());
        produtoraExistente.setPais(produtoraAtualizada.getPais());
        return produtoraRepository.save(produtoraExistente);
    }
    
    public void deletar(Long id) {
        if (!produtoraRepository.existsById(id)) {
            throw new RuntimeException("Produtora não encontrada com ID: " + id);
        }
        produtoraRepository.deleteById(id);
    }
    
    public boolean existePorNome(String nome) {
        return produtoraRepository.existsByNomeIgnoreCase(nome);
    }
}
