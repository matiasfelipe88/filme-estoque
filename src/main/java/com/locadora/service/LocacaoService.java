package com.locadora.service;

import com.locadora.model.Cliente;
import com.locadora.model.Filme;
import com.locadora.model.Locacao;
import com.locadora.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LocacaoService {
    
    @Autowired
    private LocacaoRepository locacaoRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private FilmeService filmeService;
    
    public List<Locacao> listarTodos() {
        return locacaoRepository.findAll();
    }
    
    public Optional<Locacao> buscarPorId(Long id) {
        return locacaoRepository.findById(id);
    }
    
    public List<Locacao> buscarPorCliente(Long clienteId) {
        return locacaoRepository.findByClienteId(clienteId);
    }
    
    public List<Locacao> buscarPorFilme(Long filmeId) {
        return locacaoRepository.findByFilmeId(filmeId);
    }
    
    public List<Locacao> buscarPorDataLocacao(LocalDate dataLocacao) {
        return locacaoRepository.findByDataLocacao(dataLocacao);
    }
    
    public List<Locacao> buscarLocacoesAbertas() {
        return locacaoRepository.findByDataDevolucaoIsNull();
    }
    
    public List<Locacao> buscarLocacoesFechadas() {
        return locacaoRepository.findByDataDevolucaoIsNotNull();
    }
    
    public List<Locacao> buscarLocacoesAtrasadas() {
        LocalDate dataLimite = LocalDate.now().minusDays(7); // Considerando 7 dias como prazo
        return locacaoRepository.findLocacoesAtrasadas(dataLimite);
    }
    
    public List<Locacao> buscarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        return locacaoRepository.findByDataLocacaoBetween(dataInicio, dataFim);
    }
    
    public Locacao realizarLocacao(Long clienteId, Long filmeId) {
        // Verificar se o cliente existe
        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));
        
        // Verificar se o filme existe
        Filme filme = filmeService.buscarPorId(filmeId)
                .orElseThrow(() -> new RuntimeException("Filme não encontrado com ID: " + filmeId));
        
        // Verificar se o filme já está locado (não devolvido)
        List<Locacao> locacoesAbertas = locacaoRepository.findByFilmeId(filmeId)
                .stream()
                .filter(locacao -> locacao.getDataDevolucao() == null)
                .toList();
        
        if (!locacoesAbertas.isEmpty()) {
            throw new RuntimeException("Filme já está locado e não foi devolvido");
        }
        
        Locacao locacao = new Locacao(cliente, filme, LocalDate.now());
        return locacaoRepository.save(locacao);
    }
    
    public Locacao devolverFilme(Long locacaoId) {
        Locacao locacao = locacaoRepository.findById(locacaoId)
                .orElseThrow(() -> new RuntimeException("Locação não encontrada com ID: " + locacaoId));
        
        if (locacao.getDataDevolucao() != null) {
            throw new RuntimeException("Filme já foi devolvido");
        }
        
        locacao.setDataDevolucao(LocalDate.now());
        return locacaoRepository.save(locacao);
    }
    
    public void deletar(Long id) {
        if (!locacaoRepository.existsById(id)) {
            throw new RuntimeException("Locação não encontrada com ID: " + id);
        }
        locacaoRepository.deleteById(id);
    }
    
    public boolean isFilmeDisponivel(Long filmeId) {
        List<Locacao> locacoesAbertas = locacaoRepository.findByFilmeId(filmeId)
                .stream()
                .filter(locacao -> locacao.getDataDevolucao() == null)
                .toList();
        return locacoesAbertas.isEmpty();
    }
}
