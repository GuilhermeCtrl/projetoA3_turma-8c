package com.sistema.backend.controller;

import com.sistema.backend.model.Equipe;
import com.sistema.backend.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeRepository equipeRepository;

    @GetMapping
    public List<Equipe> listar() {
        return equipeRepository.findAll();
    }

    @PostMapping
    public Equipe criar(@RequestBody Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!equipeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        equipeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipe> atualizar(@PathVariable Long id, @RequestBody Equipe equipeAtualizada) {
        return equipeRepository.findById(id).map(equipe -> {
            equipe.setNome(equipeAtualizada.getNome());
            equipe.setProjeto(equipeAtualizada.getProjeto());
            equipeRepository.save(equipe);
            return ResponseEntity.ok(equipe);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
