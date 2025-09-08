package com.locadora.controller;

import com.locadora.model.Locacao;
import com.locadora.service.LocacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/locacoes")
@CrossOrigin(origins = "*")
public class LocacaoController {
    
    @Autowired
    private LocacaoService locacaoService;
    
    @GetMapping
    public ResponseEntity<List<Locacao>> listarTodos() {
        List<Locacao> locacoes = locacaoService.listarTodos();
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Locacao> buscarPorId(@PathVariable Long id) {
        return locacaoService.buscarPorId(id)
                .map(locacao -> ResponseEntity.ok(locacao))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Locacao>> buscarPorCliente(@PathVariable Long clienteId) {
        List<Locacao> locacoes = locacaoService.buscarPorCliente(clienteId);
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<List<Locacao>> buscarPorFilme(@PathVariable Long filmeId) {
        List<Locacao> locacoes = locacaoService.buscarPorFilme(filmeId);
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/data-locacao")
    public ResponseEntity<List<Locacao>> buscarPorDataLocacao(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataLocacao) {
        List<Locacao> locacoes = locacaoService.buscarPorDataLocacao(dataLocacao);
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/abertas")
    public ResponseEntity<List<Locacao>> buscarLocacoesAbertas() {
        List<Locacao> locacoes = locacaoService.buscarLocacoesAbertas();
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/fechadas")
    public ResponseEntity<List<Locacao>> buscarLocacoesFechadas() {
        List<Locacao> locacoes = locacaoService.buscarLocacoesFechadas();
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/atrasadas")
    public ResponseEntity<List<Locacao>> buscarLocacoesAtrasadas() {
        List<Locacao> locacoes = locacaoService.buscarLocacoesAtrasadas();
        return ResponseEntity.ok(locacoes);
    }
    
    @GetMapping("/periodo")
    public ResponseEntity<List<Locacao>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        List<Locacao> locacoes = locacaoService.buscarPorPeriodo(dataInicio, dataFim);
        return ResponseEntity.ok(locacoes);
    }
    
    @PostMapping("/realizar")
    public ResponseEntity<?> realizarLocacao(@RequestParam Long clienteId, @RequestParam Long filmeId) {
        try {
            Locacao locacao = locacaoService.realizarLocacao(clienteId, filmeId);
            return ResponseEntity.status(HttpStatus.CREATED).body(locacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverFilme(@PathVariable Long id) {
        try {
            Locacao locacao = locacaoService.devolverFilme(id);
            return ResponseEntity.ok(locacao);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/verificar-disponibilidade/{filmeId}")
    public ResponseEntity<Boolean> verificarDisponibilidade(@PathVariable Long filmeId) {
        boolean disponivel = locacaoService.isFilmeDisponivel(filmeId);
        return ResponseEntity.ok(disponivel);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            locacaoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
