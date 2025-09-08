package com.locadora.controller;

import com.locadora.model.*;
import com.locadora.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class WebController {
    
    @Autowired
    private GeneroService generoService;
    
    @Autowired
    private ProdutoraService produtoraService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private FilmeService filmeService;
    
    @Autowired
    private LocacaoService locacaoService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalFilmes", filmeService.listarTodos().size());
        model.addAttribute("totalClientes", clienteService.listarTodos().size());
        model.addAttribute("totalLocacoesAbertas", locacaoService.buscarLocacoesAbertas().size());
        model.addAttribute("totalLocacoesAtrasadas", locacaoService.buscarLocacoesAtrasadas().size());
        return "index";
    }
    
    @GetMapping("/generos")
    public String generos(Model model) {
        List<Genero> generos = generoService.listarTodos();
        model.addAttribute("generos", generos);
        model.addAttribute("genero", new Genero());
        return "generos";
    }
    
    @GetMapping("/produtoras")
    public String produtoras(Model model) {
        List<Produtora> produtoras = produtoraService.listarTodos();
        model.addAttribute("produtoras", produtoras);
        model.addAttribute("produtora", new Produtora());
        return "produtoras";
    }
    
    @GetMapping("/clientes")
    public String clientes(Model model) {
        List<Cliente> clientes = clienteService.listarTodos();
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Cliente());
        return "clientes";
    }
    
    @GetMapping("/filmes")
    public String filmes(Model model) {
        List<Filme> filmes = filmeService.listarTodos();
        List<Genero> generos = generoService.listarTodos();
        List<Produtora> produtoras = produtoraService.listarTodos();
        
        model.addAttribute("filmes", filmes);
        model.addAttribute("generos", generos);
        model.addAttribute("produtoras", produtoras);
        model.addAttribute("filme", new Filme());
        return "filmes";
    }
    
    @GetMapping("/locacoes")
    public String locacoes(Model model) {
        List<Locacao> locacoes = locacaoService.listarTodos();
        List<Cliente> clientes = clienteService.listarTodos();
        List<Filme> filmes = filmeService.listarTodos();
        
        model.addAttribute("locacoes", locacoes);
        model.addAttribute("clientes", clientes);
        model.addAttribute("filmes", filmes);
        return "locacoes";
    }
    
    // Métodos para salvar dados via formulários
    @PostMapping("/generos/salvar")
    public String salvarGenero(@ModelAttribute Genero genero) {
        try {
            generoService.salvar(genero);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/generos";
    }
    
    @PostMapping("/produtoras/salvar")
    public String salvarProdutora(@ModelAttribute Produtora produtora) {
        try {
            produtoraService.salvar(produtora);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/produtoras";
    }
    
    @PostMapping("/clientes/salvar")
    public String salvarCliente(@ModelAttribute Cliente cliente) {
        try {
            clienteService.salvar(cliente);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/clientes";
    }
    
    @PostMapping("/filmes/salvar")
    public String salvarFilme(@ModelAttribute Filme filme) {
        try {
            filmeService.salvar(filme);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/filmes";
    }
    
    @PostMapping("/locacoes/realizar")
    public String realizarLocacao(@RequestParam Long clienteId, @RequestParam Long filmeId) {
        try {
            locacaoService.realizarLocacao(clienteId, filmeId);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/locacoes";
    }
    
    @PostMapping("/locacoes/{id}/devolver")
    public String devolverFilme(@PathVariable Long id) {
        try {
            locacaoService.devolverFilme(id);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/locacoes";
    }
    
    // Métodos para editar e excluir
    @GetMapping("/generos/{id}/editar")
    public String editarGenero(@PathVariable Long id, Model model) {
        Genero genero = generoService.buscarPorId(id).orElse(new Genero());
        model.addAttribute("genero", genero);
        model.addAttribute("generos", generoService.listarTodos());
        return "generos";
    }
    
    @GetMapping("/generos/{id}/excluir")
    public String excluirGenero(@PathVariable Long id) {
        try {
            generoService.deletar(id);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/generos";
    }
    
    @GetMapping("/produtoras/{id}/editar")
    public String editarProdutora(@PathVariable Long id, Model model) {
        Produtora produtora = produtoraService.buscarPorId(id).orElse(new Produtora());
        model.addAttribute("produtora", produtora);
        model.addAttribute("produtoras", produtoraService.listarTodos());
        return "produtoras";
    }
    
    @GetMapping("/produtoras/{id}/excluir")
    public String excluirProdutora(@PathVariable Long id) {
        try {
            produtoraService.deletar(id);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/produtoras";
    }
    
    @GetMapping("/clientes/{id}/editar")
    public String editarCliente(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.buscarPorId(id).orElse(new Cliente());
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clienteService.listarTodos());
        return "clientes";
    }
    
    @GetMapping("/clientes/{id}/excluir")
    public String excluirCliente(@PathVariable Long id) {
        try {
            clienteService.deletar(id);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/clientes";
    }
    
    @GetMapping("/filmes/{id}/editar")
    public String editarFilme(@PathVariable Long id, Model model) {
        Filme filme = filmeService.buscarPorId(id).orElse(new Filme());
        List<Genero> generos = generoService.listarTodos();
        List<Produtora> produtoras = produtoraService.listarTodos();
        
        model.addAttribute("filme", filme);
        model.addAttribute("generos", generos);
        model.addAttribute("produtoras", produtoras);
        model.addAttribute("filmes", filmeService.listarTodos());
        return "filmes";
    }
    
    @GetMapping("/filmes/{id}/excluir")
    public String excluirFilme(@PathVariable Long id) {
        try {
            filmeService.deletar(id);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/filmes";
    }
    
    @GetMapping("/locacoes/{id}/excluir")
    public String excluirLocacao(@PathVariable Long id) {
        try {
            locacaoService.deletar(id);
        } catch (Exception e) {
            // Tratar erro se necessário
        }
        return "redirect:/locacoes";
    }
}
