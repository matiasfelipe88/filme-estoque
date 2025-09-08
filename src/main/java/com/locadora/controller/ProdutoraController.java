package com.locadora.controller;

import com.locadora.model.Produtora;
import com.locadora.service.ProdutoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtoras")
@CrossOrigin(origins = "*")
public class ProdutoraController {
    
    @Autowired
    private ProdutoraService produtoraService;
    
    @GetMapping
    public ResponseEntity<List<Produtora>> listarTodos() {
        List<Produtora> produtoras = produtoraService.listarTodos();
        return ResponseEntity.ok(produtoras);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produtora> buscarPorId(@PathVariable Long id) {
        return produtoraService.buscarPorId(id)
                .map(produtora -> ResponseEntity.ok(produtora))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Produtora>> buscarPorNome(@RequestParam String nome) {
        List<Produtora> produtoras = produtoraService.buscarPorNome(nome);
        return ResponseEntity.ok(produtoras);
    }
    
    @GetMapping("/buscar-por-pais")
    public ResponseEntity<List<Produtora>> buscarPorPais(@RequestParam String pais) {
        List<Produtora> produtoras = produtoraService.buscarPorPais(pais);
        return ResponseEntity.ok(produtoras);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Produtora produtora) {
        try {
            Produtora produtoraSalva = produtoraService.salvar(produtora);
            return ResponseEntity.status(HttpStatus.CREATED).body(produtoraSalva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Produtora produtora) {
        try {
            Produtora produtoraAtualizada = produtoraService.atualizar(id, produtora);
            return ResponseEntity.ok(produtoraAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            produtoraService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
