package com.locadora.controller;

import com.locadora.model.Genero;
import com.locadora.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generos")
@CrossOrigin(origins = "*")
public class GeneroController {
    
    @Autowired
    private GeneroService generoService;
    
    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {
        List<Genero> generos = generoService.listarTodos();
        return ResponseEntity.ok(generos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Genero> buscarPorId(@PathVariable Long id) {
        return generoService.buscarPorId(id)
                .map(genero -> ResponseEntity.ok(genero))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Genero>> buscarPorNome(@RequestParam String nome) {
        List<Genero> generos = generoService.buscarPorNome(nome);
        return ResponseEntity.ok(generos);
    }
    
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Genero genero) {
        try {
            Genero generoSalvo = generoService.salvar(genero);
            return ResponseEntity.status(HttpStatus.CREATED).body(generoSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Genero genero) {
        try {
            Genero generoAtualizado = generoService.atualizar(id, genero);
            return ResponseEntity.ok(generoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            generoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
