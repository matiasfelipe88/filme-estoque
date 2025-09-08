package com.locadora.controller;

import com.locadora.model.Filme;
import com.locadora.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmes")
@CrossOrigin(origins = "*")
public class FilmeController {
    
    @Autowired
    private FilmeService filmeService;
    
    @GetMapping
    public ResponseEntity<List<Filme>> listarTodos() {
        List<Filme> filmes = filmeService.listarTodos();
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        return filmeService.buscarPorId(id)
                .map(filme -> ResponseEntity.ok(filme))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Filme>> buscarPorTitulo(@RequestParam String titulo) {
        List<Filme> filmes = filmeService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-ano")
    public ResponseEntity<List<Filme>> buscarPorAno(@RequestParam Integer ano) {
        List<Filme> filmes = filmeService.buscarPorAno(ano);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-genero")
    public ResponseEntity<List<Filme>> buscarPorGenero(@RequestParam Long generoId) {
        List<Filme> filmes = filmeService.buscarPorGenero(generoId);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-produtora")
    public ResponseEntity<List<Filme>> buscarPorProdutora(@RequestParam Long produtoraId) {
        List<Filme> filmes = filmeService.buscarPorProdutora(produtoraId);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-classificacao")
    public ResponseEntity<List<Filme>> buscarPorClassificacao(@RequestParam String classificacao) {
        List<Filme> filmes = filmeService.buscarPorClassificacaoEtaria(classificacao);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-titulo-e-ano")
    public ResponseEntity<List<Filme>> buscarPorTituloEAno(@RequestParam String titulo, @RequestParam Integer ano) {
        List<Filme> filmes = filmeService.buscarPorTituloEAno(titulo, ano);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-genero-nome")
    public ResponseEntity<List<Filme>> buscarPorGeneroNome(@RequestParam String genero) {
        List<Filme> filmes = filmeService.buscarPorGeneroNome(genero);
        return ResponseEntity.ok(filmes);
    }
    
    @GetMapping("/buscar-por-produtora-nome")
    public ResponseEntity<List<Filme>> buscarPorProdutoraNome(@RequestParam String produtora) {
        List<Filme> filmes = filmeService.buscarPorProdutoraNome(produtora);
        return ResponseEntity.ok(filmes);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Filme filme) {
        try {
            Filme filmeSalvo = filmeService.salvar(filme);
            return ResponseEntity.status(HttpStatus.CREATED).body(filmeSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Filme filme) {
        try {
            Filme filmeAtualizado = filmeService.atualizar(id, filme);
            return ResponseEntity.ok(filmeAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            filmeService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
