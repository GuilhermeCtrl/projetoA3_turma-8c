package com.sistema.backend.controller;

import com.sistema.backend.model.Equipe;
import com.sistema.backend.repository.EquipeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeRepository equipeRepository;

    // Listar todas as equipes
    @GetMapping
    public List<Equipe> listar() {
        return equipeRepository.findAll();
    }

    // Criar equipe
    @PostMapping
    public Equipe criar(@RequestBody Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    // Atualizar equipe (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Equipe> atualizar(@PathVariable Long id, @RequestBody Equipe equipeAtualizada) {
        return equipeRepository.findById(id).map(equipeExistente -> {
            equipeExistente.setNome(equipeAtualizada.getNome());
            return ResponseEntity.ok(equipeRepository.save(equipeExistente));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar parcialmente (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<Equipe> atualizarParcial(@PathVariable Long id, @RequestBody Equipe equipeAtualizada) {
        return equipeRepository.findById(id).map(equipeExistente -> {
            if (equipeAtualizada.getNome() != null) {
                equipeExistente.setNome(equipeAtualizada.getNome());
            }
            return ResponseEntity.ok(equipeRepository.save(equipeExistente));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar equipe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!equipeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        equipeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
