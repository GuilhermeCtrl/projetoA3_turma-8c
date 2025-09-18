package com.sistema.backend.controller;

import com.sistema.backend.model.Projeto;
import com.sistema.backend.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoRepository projetoRepository;

    @GetMapping
    public List<Projeto> listar() {
        return projetoRepository.findAll();
    }

    @PostMapping
    public Projeto criar(@RequestBody Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!projetoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projetoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizar(@PathVariable Long id, @RequestBody Projeto projetoAtualizado) {
        return projetoRepository.findById(id).map(projeto -> {
            projeto.setNome(projetoAtualizado.getNome());
            projeto.setEquipes(projetoAtualizado.getEquipes());
            projetoRepository.save(projeto);
            return ResponseEntity.ok(projeto);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
