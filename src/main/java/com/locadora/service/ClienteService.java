package com.locadora.service;

import com.locadora.model.Cliente;
import com.locadora.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }
    
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clienteRepository.findByCpf(cpf);
    }
    
    public Optional<Cliente> buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public Cliente salvar(Cliente cliente) {
        // Verificar se já existe um cliente com o mesmo CPF
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new RuntimeException("Já existe um cliente com este CPF: " + cliente.getCpf());
        }
        
        // Verificar se já existe um cliente com o mesmo email
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Já existe um cliente com este email: " + cliente.getEmail());
        }
        
        return clienteRepository.save(cliente);
    }
    
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        
        // Verificar se o novo CPF já existe em outro cliente
        if (!clienteExistente.getCpf().equals(clienteAtualizado.getCpf()) &&
            clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
            throw new RuntimeException("Já existe um cliente com este CPF: " + clienteAtualizado.getCpf());
        }
        
        // Verificar se o novo email já existe em outro cliente
        if (!clienteExistente.getEmail().equalsIgnoreCase(clienteAtualizado.getEmail()) &&
            clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
            throw new RuntimeException("Já existe um cliente com este email: " + clienteAtualizado.getEmail());
        }
        
        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        return clienteRepository.save(clienteExistente);
    }
    
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    public boolean existePorCpf(String cpf) {
        return clienteRepository.existsByCpf(cpf);
    }
    
    public boolean existePorEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
}
